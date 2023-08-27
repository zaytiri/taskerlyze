package libraries.sqlquerybuilder;

import libraries.sqlquerybuilder.helpers.DatabaseTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.InsertQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertQueryBuilderTests {
    private final static Logger LOGGER = Logger.getLogger(InsertQueryBuilderTests.class.getName());

    @BeforeAll
    public static void createDatabaseSchema() {
        DatabaseTestHelper.createDatabaseSchema();
        DatabaseTestHelper.createTables();
    }

    @AfterAll
    public static void deleteDatabase() {
        DatabaseTestHelper.deleteDatabase();
    }

    @Test
    void Should_InsertInAllColumnsIntoTableTask() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table tasks = DatabaseTestHelper.getSchema().getTable("tasks");

            // act
            InsertQueryBuilder query = new InsertQueryBuilder(connection);
            query.setCloseConnection(false);
            query.insertInto(tasks).values(1, "a task name");
            Response response = query.execute();
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from tasks where id=1");
            rs.next();

            Assertions.assertEquals("a task name", rs.getString("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_InsertInAllColumnsIntoTableTask: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_InsertOnlyInNameColumnIntoTablePeople() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table people = DatabaseTestHelper.getSchema().getTable("people");
            Column name = people.getColumn("name");

            // act
            InsertQueryBuilder query = new InsertQueryBuilder(connection);
            query.setCloseConnection(false);
            query.insertInto(people, new ArrayList<>() {{
                add(name);
            }}).values("Michael");
            Response response = query.execute();
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from people");
            rs.next();

            Assertions.assertEquals("Michael", rs.getString("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_InsertOnlyInNameColumnIntoTablePeople: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }
}
