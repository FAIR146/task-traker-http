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
        task.setUserName(rs.getString("user_name"));
        return task;
    };
    private final RowMapper<Epic> epicRowMapper = (rs, rowNum) -> {
        Epic epic = new Epic();
        epic.setId(rs.getLong("id"));
        epic.setName(rs.getString("epic_name"));
        epic.setDescription(rs.getString("description"));
        epic.setUserName(rs.getString("user_name"));

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
                .usingColumns("name", "description", "status_id", "user_id")
                .usingGeneratedKeyColumns("id");

        this.simpleJdbcInsertEpic = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("epic")
                .usingColumns("name", "description", "user_id")
                .usingGeneratedKeyColumns("id");

        this.simpleJdbcInsertSubTask = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("subTask")
                .usingColumns("name", "description", "status_id", "epic_id")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long addTask(Task task) {
        String sqlSelectStatusIdByName = "SELECT id FROM status WHERE name = ?";
        String sqlGetUserIdByUserName = "SELECT id FROM \"user\" WHERE name = ?";
        int statusId = jdbcTemplate.queryForObject(sqlSelectStatusIdByName, Integer.class);
        int userId = jdbcTemplate.queryForObject(sqlGetUserIdByUserName, Integer.class, task.getUserName());
        HashMap<String, Object> hashMap = new HashMap<>(){{
            put("name", task.getName());
            put("description", task.getDescription());
            put("status_id", statusId);
            put("user_id", userId);
        }};
        return simpleJdbcInsertTask.executeAndReturnKey(hashMap).longValue();
    }

    @Override
    public long addEpic(Epic epic) {
        String sqlGetUserIdByUserName = "SELECT id FROM \"user\" WHERE name = ?";
        int userId = jdbcTemplate.queryForObject(sqlGetUserIdByUserName, Integer.class, epic.getUserName());
        HashMap<String, Object> hashMap = new HashMap<>(){{
            put("name", epic.getName());
            put("description", epic.getDescription());
            put("user_name", epic.getUserName());
            put("user_id", userId);
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
        String getEpicSql = "SELECT epic.id, epic.name as epic_name, epic.description, user_id, \"user\".name AS user_name\n" +
                "FROM epic JOIN \"user\" ON epic.user_id = \"user\".id WHERE epic.id = ?;";

        List<SubTask> subTasks = getSubTasksByEpicId(id);

        try {
            Epic epic = jdbcTemplate.queryForObject(getEpicSql, epicRowMapper, id);
            epic.setSubTasks(subTasks);
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
        String sql = "SELECT task.id, task.name, description, status.name AS status, \"user\".name as user_name " +
                "FROM " +
                "task " +
                "JOIN status ON status.id = task.status_id " +
                "JOIN \"user\" on task.user_id = \"user\".id " +
                "WHERE task.id = ?";

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
                "description = ?," +
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

    @Override
    public List<Task> getTaskByStatusAndUserName(Status status, String username) {
        String sql = "SELECT task.id, task.name, description, status.name AS status, \"user\".name as user_name " +
                "FROM " +
                "task " +
                "JOIN status ON status.id = task.status_id " +
                "JOIN \"user\" on task.user_id = \"user\".id " +
                "WHERE status.name = ? AND \"user\".name = ?";
        return jdbcTemplate.query(sql, taskRowMapper, status.name(), username);
    }

    public List<Task> getTasksByUsername(String name) {
        String sql = "SELECT task.id, task.name, description, status.name AS status, \"user\".name as user_name " +
                "FROM " +
                "task " +
                "JOIN status ON status.id = task.status_id " +
                "JOIN \"user\" on task.user_id = \"user\".id " +
                "WHERE task.user_name = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setStatus(Status.valueOf(rs.getString("status")));
            task.setUserName(rs.getString("user_name"));
            return task;
        });
    }

    @Override
    public List<Epic> getEpicByUsername(String name) {
        String sql = "SELECT epic.id, epic.name, description, \"user\".name as user_name " +
                "FROM " +
                "epic " +
                "JOIN \"user\" on epic.user_id = \"user\".id " +
                "WHERE epic.user_name = ?";

        //TODO наполнить сабтасками
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Epic epic = epicRowMapper.mapRow(rs, rowNum);
            if (epic != null) {
                List<SubTask> subTasks = getSubTasksByEpicId(epic.getId());
                epic.setSubTasks(subTasks);
            }

            return epic;
        });
    }
    @Override
    public List<SubTask> getSubTasksByEpicId (long id) {
        String getSubtasksSql = "SELECT subtask.id, subtask.name, subtask.description, epic_id, status.name AS status " +
                "FROM subTask JOIN status ON subTask.status_id = status.id WHERE epic_id = ?";

        List<SubTask> subTasks = jdbcTemplate.query(getSubtasksSql, subTaskRowMapper, id);

        return subTasks;
    }

}
