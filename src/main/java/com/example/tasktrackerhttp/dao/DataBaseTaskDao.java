package com.example.tasktrackerhttp.dao;

import com.example.tasktrackerhttp.dto.Epic;
import com.example.tasktrackerhttp.dto.Status;
import com.example.tasktrackerhttp.dto.SubTask;
import com.example.tasktrackerhttp.dto.Task;
import jakarta.annotation.Nullable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

@Repository
@ConditionalOnProperty(name = "taskTracker.task.dao.implementation", havingValue = "DataBaseTaskDao")
public class DataBaseTaskDao implements TaskDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertTask;
    private final SimpleJdbcInsert simpleJdbcInsertEpic;
    private final SimpleJdbcInsert simpleJdbcInsertSubTask;
    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setName(rs.getString("name"));
        task.setDescription(rs.getString("description"));
        task.setStatus(Status.valueOf(rs.getString("status")));
        return task;
    };
    private final RowMapper<Epic> epicRowMapper = (rs, rowNum) -> {
        Epic epic = new Epic();
        epic.setId(rs.getLong("id"));
        epic.setName(rs.getString("name"));
        epic.setDescription(rs.getString("description"));

        return epic;
    };
    private final RowMapper<SubTask> subTaskRowMapper = (rs, rowNum) -> {
        SubTask subTask = new SubTask();
        subTask.setId(rs.getLong("id"));
        subTask.setName(rs.getString("name"));
        subTask.setDescription(rs.getString("description"));
        subTask.setStatus(Status.valueOf(rs.getString("status")));
        subTask.setEpicId(rs.getInt("epic_id"));
        return subTask;

    };
    DataBaseTaskDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsertTask = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("task")
                .usingColumns("name", "description", "status_id")
                .usingGeneratedKeyColumns("id");

        this.simpleJdbcInsertEpic = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("epic")
                .usingColumns("name", "description")
                .usingGeneratedKeyColumns("id");

        this.simpleJdbcInsertSubTask = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("subTask")
                .usingColumns("name", "description", "status_id", "epic_id")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long addTask(Task task) {
        String sqlSelectStatusIdByName = "SELECT id FROM status WHERE name = 'NEW'";
        int statusId = jdbcTemplate.queryForObject(sqlSelectStatusIdByName, Integer.class);
        HashMap<String, Object> hashMap = new HashMap<>(){{
            put("name", task.getName());
            put("description", task.getDescription());
            put("status_id", statusId);
        }};
        return simpleJdbcInsertTask.executeAndReturnKey(hashMap).longValue();
    }

    @Override
    public long addEpic(Epic epic) {
        HashMap<String, Object> hashMap = new HashMap<>(){{
            put("name", epic.getName());
            put("description", epic.getDescription());
        }};
        return simpleJdbcInsertEpic.executeAndReturnKey(hashMap).longValue();
    }

    @Override
    public long addSubTask(SubTask subTask) {
        String sqlSelectStatusIdByName = "SELECT id FROM status WHERE name = 'NEW'";
        int statusId = jdbcTemplate.queryForObject(sqlSelectStatusIdByName, Integer.class);
        HashMap<String, Object> hashMap = new HashMap<>(){{
            put("name", subTask.getName());
            put("description", subTask.getDescription());
            put("status_id", statusId);
            put("epic_id", subTask.getEpicId());

        }};
        return simpleJdbcInsertSubTask.executeAndReturnKey(hashMap).longValue();
    }

    @Override
    public void removeEpicById(long id) {
        String sql = "DELETE FROM epic WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void removeSubTaskById(long id) {
        String sql = "DELETE FROM subTask WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void removeTaskById(long id) {
        String sql = "DELETE FROM task WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Epic getEpicById(long id) {
        String sql = "SELECT epic.id, epic.description, status_id, user_id, \"user\".name AS user_name\n" +
                "FROM epic JOIN \"user\" ON epic.user_id = \"user\".id WHERE epic.id = ?;";
        String sqlGetSubTaskId = "SELECT id FROM subTask WHERE epic_id = ?";
        String sqlGetAllSubTasksEpic = "SELECT subtask.id, subtask.name, subtask.description, epic_id, status.name AS status " +
                "FROM subTask JOIN status ON status.id = subtask.id WHERE epic_id = ?";
        List<SubTask> subTasks = jdbcTemplate.query(sqlGetAllSubTasksEpic, subTaskRowMapper, id);
        List<Long> subTasksId = jdbcTemplate.queryForList(sqlGetSubTaskId, Long.class, id);

        try {
            Epic epic = jdbcTemplate.queryForObject(sql, epicRowMapper, id);
            epic.setSubTasksId(subTasksId);
            epic.setStatus(getEpicStatus(subTasks));
            return epic;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public SubTask getSubTaskById(long id) {
        String sql = "SELECT subtask.id, subtask.name, description, status.name AS status, subtask.epic_id " +
                "FROM subtask JOIN status ON status.id = subTask.status_id WHERE subtask.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, subTaskRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public Task getTaskById(long id) {
        String sql = "SELECT task.id, task.name, description, status_id, user_id, \"user\".name AS user_name\n" +
                "FROM task JOIN \"user\" ON task.user_id = \"user\".id WHERE task.id = ?;";

        try {
            return jdbcTemplate.queryForObject(sql, taskRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void updateTask(Task task) {
        String sql = "UPDATE task SET " +
                "name = ?, " +
                "description = ?, " +
                "status_id = (SELECT status.id FROM status WHERE name = ?) " +
                "WHERE id = ?";


        jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getStatus().name(), task.getId());
    }

    @Override
    public void updateEpic(Epic epic) {
        String sql = "UPDATE epic SET " +
                "name = ?, " +
                "description = ?";
        jdbcTemplate.update(sql, epic.getName(), epic.getDescription());
    }

    @Override
    public void updateSubTask (SubTask subTask) {
        String sql = "UPDATE subTask SET " +
                "name = ?, " +
                "description = ?, " +
                "status_id = (SELECT status.id FROM status WHERE name = ?) " +
                "WHERE subTask.id = ?";
        jdbcTemplate.update(sql, subTask.getName(), subTask.getDescription(), subTask.getStatus().name(), subTask.getId()); // subTask.getEpicId()
    }
    public Status getEpicStatus (List<SubTask> list) {

        int statusNew = 0;
        int statusInProgress = 0;
        int statusDone = 0;

        for (int i = 0; i < list.size(); i++) {
            Status currentStatus = list.get(i).getStatus();
            if (currentStatus == Status.IN_PROGRESS) {
                statusInProgress++;
            } else if (currentStatus == Status.NEW) {
                statusNew++;
            } else if (currentStatus == Status.DONE) {
                statusDone++;
            }
        }

        Status status = Status.UNDEFINED;
        if (statusInProgress == 0 && statusDone == 0) {
            status = Status.NEW;
        } else if (statusDone > 0){
            status = Status.IN_PROGRESS;
        } else if (statusNew == 0 && statusInProgress == 0) {
            status = Status.DONE;
        }
        return status;
    }
    public List<Task> getTasksByUsername(String name) {
        return null;
    }

}
