package libraries.sqlquerybuilder;

import libraries.sqlquerybuilder.helpers.DatabaseTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.DeleteQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.Operators;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteQueryBuilderTests {
    private final static Logger LOGGER = Logger.getLogger(DeleteQueryBuilderTests.class.getName());

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
    void Should_DeleteAllInTablePeople() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table people = DatabaseTestHelper.getSchema().getTable("people");
            int rowCountBefore = DatabaseTestHelper.getRowCount(connection, "select * from people");

            // act
            DeleteQueryBuilder query = new DeleteQueryBuilder(connection);
            query.setCloseConnection(false);
            query.deleteFrom(people);
            Response response = query.execute();
            int rowCountAfter = DatabaseTestHelper.getRowCount(connection, "select * from people");
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

            Assertions.assertEquals(6, rowCountBefore);
            Assertions.assertEquals(0, rowCountAfter);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_DeleteAllInTablePeople: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_DeleteRowWhereIdEquals2InTasksTable() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table tasks = DatabaseTestHelper.getSchema().getTable("tasks");
            Column id = tasks.getColumn("id");
            int rowCountBefore = DatabaseTestHelper.getRowCount(connection, "select * from tasks where id=2");

            // act
            DeleteQueryBuilder query = new DeleteQueryBuilder(connection);
            query.setCloseConnection(false);
            query.deleteFrom(tasks).where(id, Operators.EQUALS, 2);
            Response response = query.execute();
            int rowCountAfter = DatabaseTestHelper.getRowCount(connection, "select * from tasks where id=2");
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

            Assertions.assertEquals(1, rowCountBefore);
            Assertions.assertEquals(0, rowCountAfter);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_DeleteRowWhereIdEquals2InTasksTable: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }
}
