package app.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.DbConnection;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskTests {
    private final static Logger LOGGER = Logger.getLogger(TaskTests.class.getName());

    @BeforeEach
    public void createDatabase() {
        DbConnection.getInstance();
    }

    @AfterEach
    public void deleteDatabase() {
        try {
            String currentDirectory = Path.of("").toAbsolutePath().toString();
            String databaseName = "taskerlyze.db";

            Files.delete(Path.of(currentDirectory + "\\src\\main\\resources\\database\\" + databaseName));
            DbConnection.getInstance().resetInstance();
        } catch (Exception e) {
            // if any error occurs
            LOGGER.log(Level.SEVERE, "deleteDatabase: " + e.getMessage(), e);
        }
    }

    public static void insertMockData(List<String> queries) {
        Connection connection = null;
        try {
            // create a database connection
            String currentDirectory = Path.of("").toAbsolutePath().toString();
            connection = DriverManager.getConnection("jdbc:sqlite:" + currentDirectory + "\\src\\main\\resources\\database\\taskerlyze.db");

            Statement statement = connection.createStatement();
            for (String query : queries) {
                statement.executeUpdate(query);
            }
            statement.close();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "insertMockData: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Test
    void Should_CreateANewTask() {
        // arrange
        TaskController controller = new TaskController();

        Task task = Task.getInstance();
        task.setName("a new task")
                .setDescription("a new task to test the task");

        // act
        OperationResult<Task> result = controller.createOrUpdate(task);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(1, result.getResult().getId());
    }

    @Test
    void Should_DeleteATaskFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('do something good', false, 'i need to do something good', " + new Date().getTime() + ", " + new Date().getTime() + ")");
            }
        };
        insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        OperationResult<Task> result = controller.delete(1);

        // assert
        Assertions.assertTrue(result.getStatus());
    }

    @Test
    void Should_GetAListOfCreatedTasksAfterCurrentDayFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create diagrams', true, 'do relation diagrams for all classes', " + getDate(Calendar.HOUR, 12) + ", " + getDate(Calendar.HOUR, -12) + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create scripts', true, 'do some python scripts to help', " + getDate(Calendar.HOUR, 56) + ", " + getDate(Calendar.HOUR, 48) + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create documentation', false, 'do documentation for necessary methods', " + getDate(Calendar.HOUR, -24) + ", " + getDate(Calendar.HOUR, -48) + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create design', false, 'do designs for frontend', " + getDate(Calendar.HOUR, -24) + ", " + getDate(Calendar.HOUR, 24) + ")");
            }
        };
        insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("created_at", new Pair<>(">=", getDate(Calendar.MINUTE, -24)));

        OperationResult<List<Task>> result = controller.get(filters);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(2, result.getResult().size());
    }

    @Test
    void Should_GetAListOfTasksDoneFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create diagrams', true, 'do relation diagrams for all classes', " + getDate(Calendar.MINUTE, 12) + ", " + new Date().getTime() + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create scripts', true, 'do some python scripts to help', " + getDate(Calendar.MINUTE, 56) + ", " + new Date().getTime() + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create documentation', false, 'do documentation for necessary methods', " + getDate(Calendar.HOUR, -24) + ", " + getDate(Calendar.HOUR, -24) + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create design', false, 'do designs for frontend', " + getDate(Calendar.HOUR, -24) + ", " + getDate(Calendar.HOUR, -24) + ")");
            }
        };
        insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("is_done", new Pair<>("=", true));

        OperationResult<List<Task>> result = controller.get(filters);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(2, result.getResult().size());
        Assertions.assertEquals("create diagrams", result.getResult().get(0).getName());
        Assertions.assertEquals("create scripts", result.getResult().get(1).getName());
    }

    @Test
    void Should_GetATaskFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('do something good', false, 'i need to do something good', " + new Date().getTime() + ", " + new Date().getTime() + ")");
            }
        };
        insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        OperationResult<Task> result = controller.get(1);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(1, result.getResult().getId());
        Assertions.assertEquals("do something good", result.getResult().getName());
        Assertions.assertFalse(result.getResult().isDone(false));
        Assertions.assertEquals("i need to do something good", result.getResult().getDescription());
    }

    @Test
    void Should_SetUndoneTaskToDoneFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create diagrams', true, 'do relation diagrams for all classes', " + getDate(Calendar.MINUTE, 12) + ", " + new Date().getTime() + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create scripts', true, 'do some python scripts to help', " + getDate(Calendar.MINUTE, 56) + ", " + new Date().getTime() + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create documentation', false, 'do documentation for necessary methods', " + getDate(Calendar.HOUR, -24) + ", " + getDate(Calendar.HOUR, -24) + ")");
                add("insert into tasks (name, is_done, description, updated_at, created_at) values ('create design', false, 'do designs for frontend', " + getDate(Calendar.HOUR, -24) + ", " + getDate(Calendar.HOUR, -24) + ")");
            }
        };
        insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        OperationResult<Task> result = controller.setDone(3);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertTrue(result.getResult().isDone(false));
    }

    private static long getDate(int calendarParcel, int minutesToAdd) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(calendarParcel, minutesToAdd);

        return cal.getTime().getTime();
    }
}
