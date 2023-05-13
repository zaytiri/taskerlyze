package sqlquerybuilder;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.Operators;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.SelectQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectQueryBuilderTests {
    private final static Logger LOGGER = Logger.getLogger(SelectQueryBuilderTests.class.getName());

    @BeforeAll
    public static void createDatabaseSchema() {
        DatabaseTestHelper.createDatabaseSchema();
        DatabaseTestHelper.createTables();
        DatabaseTestHelper.insertMockData();
    }

    @AfterAll
    public static void deleteDatabase() {
        DatabaseTestHelper.deleteDatabase();
    }

    @Test
    void Should_ResponseUnsuccessfulWhenSelectingANonExistentIdFromTasksTable() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table tasks = DatabaseTestHelper.getSchema().getTable("tasks");

            // act
            SelectQueryBuilder query = new SelectQueryBuilder(connection);

            Column id = tasks.getColumn("id");
            query.select().from(tasks).where(id, Operators.EQUALS, "999");
            Response response = query.execute();

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_ResponseUnsuccessfulWhenSelectingANonExistentIdFromTasksTable: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_SelectAllNamesFromTasksTable() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table table = DatabaseTestHelper.getSchema().getTable("tasks");

            // act
            SelectQueryBuilder query = new SelectQueryBuilder(connection);

            List<Column> name = table.getColumns(new ArrayList<>() {{
                add("name");
            }});

            query.select(name).from(table);
            Response response = query.execute();

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(3, response.getResult().size());

            Assertions.assertEquals("do something weird", response.getResult().get(0).get("name"));

            Assertions.assertEquals("dont do that", response.getResult().get(1).get("name"));

            Assertions.assertEquals("do anything spectacular", response.getResult().get(2).get("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_SelectAllNamesFromTasksTable: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_SelectAllRowsFromPeopleTable() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table table = DatabaseTestHelper.getSchema().getTable("people");

            // act
            SelectQueryBuilder query = new SelectQueryBuilder(connection);
            query.select().from(table);
            Response response = query.execute();

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(4, response.getResult().size());

            Assertions.assertEquals("1", response.getResult().get(0).get("id"));
            Assertions.assertEquals("cat", response.getResult().get(0).get("name"));

            Assertions.assertEquals("2", response.getResult().get(1).get("id"));
            Assertions.assertEquals("nuno", response.getResult().get(1).get("name"));

            Assertions.assertEquals("3", response.getResult().get(2).get("id"));
            Assertions.assertEquals("pedro", response.getResult().get(2).get("name"));

            Assertions.assertEquals("4", response.getResult().get(3).get("id"));
            Assertions.assertEquals("monica", response.getResult().get(3).get("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_SelectAllRowsFromPeopleTable: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_SelectAllRowsWhereTasksJoinsPeople() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table tableTasks = DatabaseTestHelper.getSchema().getTable("tasks");
            Table tablePeople = DatabaseTestHelper.getSchema().getTable("people");
            Table tableTasksPeople = DatabaseTestHelper.getSchema().getTable("tasks_people");

            // act
            SelectQueryBuilder query = new SelectQueryBuilder(connection);

            Column taskId = tableTasks.getColumn("id");
            Column tasksId = tableTasksPeople.getColumn("tasks_id");

            Column personId = tablePeople.getColumn("id");
            Column peopleId = tableTasksPeople.getColumn("people_id");

            query.select()
                    .from(tableTasks)
                    .join(tableTasksPeople)
                    .on(taskId, tasksId)
                    .join(tablePeople)
                    .on(peopleId, personId);

            Response response = query.execute();

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(3, response.getResult().size());

            Assertions.assertEquals("monica", response.getResult().get(2).get("people__name"));
            Assertions.assertEquals("2", response.getResult().get(1).get("tasks__id"));
            Assertions.assertEquals("1", response.getResult().get(0).get("tasks_people__people_id"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_SelectAllRowsWhereTasksJoinsPeople: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_SelectTaskWithId3FromTasksTable() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table table = DatabaseTestHelper.getSchema().getTable("tasks");

            // act
            SelectQueryBuilder query = new SelectQueryBuilder(connection);

            Column id = table.getColumn("id");
            query.select().from(table).where(id, Operators.EQUALS, "3");
            Response response = query.execute();

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(1, response.getResult().size());

            Assertions.assertEquals("3", response.getResult().get(0).get("id"));
            Assertions.assertEquals("do anything spectacular", response.getResult().get(0).get("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_SelectTaskWithId3FromTasksTable: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }
}
