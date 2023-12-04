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
    public void removeAllTasks() {
        jdbcTemplate.update("DELETE FROM task");
    }

    @Override
    public void removeAllEpics() {
        jdbcTemplate.update("DELETE FROM epic");
    }

    @Override
    public void removeAllSubTasks() {
        jdbcTemplate.update("DELETE FROM subTask");
    }

    @Override
    public List<Task> getAllTasks() {
        String sql = "SELECT task.id,task.name, description, status.name AS status " +
                "FROM task JOIN status ON status.id = task.status_id";
        List<Task> tasks = jdbcTemplate.query(sql, taskRowMapper);
        return tasks;
    }

    @Override
    public List<Epic> getAllEpics() {
        String sql = "SELECT epic.id, epic.name, description, status.name AS status " +
                "FROM epic JOIN status ON status.id = epic.status_id";
        List<Epic> epics = jdbcTemplate.query(sql, epicRowMapper);
        return epics;
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        String sql = "SELECT subtask.id, subtask.name, description, status.name AS status " +
                "FROM subtask JOIN status ON status.id = subtask.status_id";
        List<SubTask> subTasks = jdbcTemplate.query(sql, subTaskRowMapper);
        return subTasks;
    }

    @Override
    public Epic getEpicById(long id) {
        String sql = "SELECT id, name, description FROM epic WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, epicRowMapper, id);
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
        String sql = "SELECT task.id,task.name, description, status.name AS status " +
                "FROM task JOIN status ON status.id = task.status_id WHERE task.id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, taskRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void updateEpic(Epic epic) {

    }

    @Override
    public void updateSubTask(SubTask subTask) {

    }
}
