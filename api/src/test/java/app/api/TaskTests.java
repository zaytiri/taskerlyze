package app.api;

import app.api.helpers.ApiTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import personal.zaytiri.makeitexplicitlyqueryable.pairs.Pair;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.DbConnection;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class TaskTests {
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

    @Test
    void Should_CreateANewTask() {
        // arrange
        TaskController controller = new TaskController();

        Task task = new Task().getInstance();
        task.setName("a new task")
                .setDescription("a new task to test the task");

        // act
        OperationResult<Task> result = controller.create(task);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(1, result.getResult().getId());
    }

    @Test
    void Should_DeleteATaskFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("do something good");
                    add(false);
                    add("i need to do something good");
                    add(0);
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

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
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create diagrams");
                    add(true);
                    add("do relation diagrams for all classes");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 12));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -12));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create scripts");
                    add(true);
                    add("do some python scripts to help");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 56));
                    add(ApiTestHelper.getDate(Calendar.HOUR, 48));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create documentation");
                    add(false);
                    add("do documentation for necessary methods");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -48));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create design");
                    add(false);
                    add("do designs for frontend");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, 24));
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("created_at", new Pair<>(">=", ApiTestHelper.getDate(Calendar.MINUTE, -24)));
        OperationResult<List<Task>> result = controller.get(filters, null);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(2, result.getResult().size());
    }

    @Test
    void Should_GetAListOfOrderedTasksInAscendingFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create diagrams");
                    add(true);
                    add("do relation diagrams for all classes");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 12));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -12));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create scripts");
                    add(true);
                    add("do some python scripts to help");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 56));
                    add(ApiTestHelper.getDate(Calendar.HOUR, 48));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create documentation");
                    add(false);
                    add("do documentation for necessary methods");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -48));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create design");
                    add(false);
                    add("do designs for frontend");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, 24));
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        Pair<String, String> orderByColumn = new Pair<>("asc", "created_at");

        OperationResult<List<Task>> result = controller.get(filters, orderByColumn);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(4, result.getResult().size());

        Assertions.assertEquals(3, result.getResult().get(0).getId());
        Assertions.assertEquals(1, result.getResult().get(1).getId());
        Assertions.assertEquals(4, result.getResult().get(2).getId());
        Assertions.assertEquals(2, result.getResult().get(3).getId());
    }

    @Test
    void Should_GetAListOfTasksDoneFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create diagrams");
                    add(true);
                    add("do relation diagrams for all classes");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 12));
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create scripts");
                    add(true);
                    add("do some python scripts to help");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 56));
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create documentation");
                    add(false);
                    add("do documentation for necessary methods");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create design");
                    add(false);
                    add("do designs for frontend");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("is_done", new Pair<>("=", true));

        OperationResult<List<Task>> result = controller.get(filters, null);

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
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("do something good");
                    add(false);
                    add("i need to do something good");
                    add(0);
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

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
    void Should_GetNoTasksByCategoryId5FromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("work");
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("personal");
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("take sandy for a walk");
                    add(false);
                    add("need to take sandy for a walk in the park for happiness");
                    add(2);
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        OperationResult<List<Task>> noCategoryTasks = controller.getTasksByCategoryAndCompletedAtDate(5, LocalDate.now());

        // assert
        Assertions.assertFalse(noCategoryTasks.getStatus());
        Assertions.assertEquals(0, noCategoryTasks.getResult().size());
    }

    @Test
    void Should_GetTasksByCategoryIdFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add(ApiTestHelper.getInsertQueryForCategoriesTable(new ArrayList<>() {{
                    add("work");
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForCategoriesTable(new ArrayList<>() {{
                    add("personal");
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create diagrams");
                    add(true);
                    add("do relation diagrams for all classes");
                    add(1);
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create scripts");
                    add(true);
                    add("do some python scripts to help");
                    add(1);
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create documentation");
                    add(false);
                    add("do documentation for necessary methods");
                    add(1);
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("take sandy for a walk");
                    add(false);
                    add("need to take sandy for a walk in the park for happiness");
                    add(2);
                    add(new Date().getTime());
                    add(new Date().getTime());
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        OperationResult<List<Task>> workTasks = controller.getTasksByCategoryAndCompletedAtDate(1, LocalDate.now());
        OperationResult<List<Task>> personalTasks = controller.getTasksByCategoryAndCompletedAtDate(2, LocalDate.now());

        // assert
        Assertions.assertTrue(workTasks.getStatus());
        Assertions.assertEquals(3, workTasks.getResult().size());

        Assertions.assertTrue(personalTasks.getStatus());
        Assertions.assertEquals(1, personalTasks.getResult().size());
    }

    @Test
    void Should_SetUndoneTaskToDoneFromDatabase() {
        // arrange
        List<String> queries = new ArrayList<>() {
            {
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create diagrams");
                    add(true);
                    add("do relation diagrams for all classes");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 12));
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create scripts");
                    add(true);
                    add("do some python scripts to help");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, 56));
                    add(new Date().getTime());
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create documentation");
                    add(false);
                    add("do documentation for necessary methods");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                }}));
                add(ApiTestHelper.getInsertQueryForTasksTable(new ArrayList<>() {{
                    add("create design");
                    add(false);
                    add("do designs for frontend");
                    add(1);
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                    add(ApiTestHelper.getDate(Calendar.HOUR, -24));
                }}));
            }
        };
        ApiTestHelper.insertMockData(queries);

        TaskController controller = new TaskController();

        // act
        OperationResult<Task> result = controller.setDone(3, true);

        // assert
        Assertions.assertTrue(result.getStatus());
        Assertions.assertTrue(result.getResult().isDone(false));
    }
}
