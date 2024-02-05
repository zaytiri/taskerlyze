package libraries.sqlquerybuilder;

import libraries.sqlquerybuilder.helpers.DatabaseTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.query.builders.UpdateQueryBuilder;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.query.enums.Operators;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateQueryBuilderTests {
    private final static Logger LOGGER = Logger.getLogger(InsertQueryBuilderTests.class.getName());

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
    void Should_UpdateRowWithId2UsingMultipleSetMethodsFromTablePeople() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table people = DatabaseTestHelper.getSchema().getTable("people");

            // act
            UpdateQueryBuilder query = new UpdateQueryBuilder(connection);
            query.setCloseConnection(false);

            query.update(people)
                    .set(people.getColumn("name"), "andrew")
                    .set(people.getColumn("id"), 88)
                    .where(people.getColumn("id"), Operators.EQUALS, 2);
            Response response = query.execute();
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from people where id=88");
            rs.next();

            Assertions.assertEquals("andrew", rs.getString("name"));
            Assertions.assertNotEquals("nuno", rs.getString("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_UpdateRowWithId2UsingMultipleSetMethodsFromTablePeople: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_UpdateRowWithId2UsingSetMethodFromTablePeople() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table people = DatabaseTestHelper.getSchema().getTable("people");

            // act
            UpdateQueryBuilder query = new UpdateQueryBuilder(connection);
            query.setCloseConnection(false);

            query.update(people)
                    .set(people.getColumn("name"), "mary")
                    .where(people.getColumn("id"), Operators.EQUALS, 3);
            Response response = query.execute();
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from people where id=3");
            rs.next();

            Assertions.assertEquals("mary", rs.getString("name"));
            Assertions.assertNotEquals("pedro", rs.getString("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_UpdateRowWithId2UsingSetMethodFromTablePeople: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }

    @Test
    void Should_UpdateRowWithId4UsingValuesMethodFromTablePeople() {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table people = DatabaseTestHelper.getSchema().getTable("people");

            // act
            UpdateQueryBuilder query = new UpdateQueryBuilder(connection);
            query.setCloseConnection(false);

            Map<Column, Object> updateValues = new HashMap<>() {{
                put(people.getColumn("name"), "kathy");
            }};

            query.update(people)
                    .values(updateValues)
                    .where(people.getColumn("id"), Operators.EQUALS, 4);
            Response response = query.execute();
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(0, response.getResult().size());

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from people where id=4");
            rs.next();

            Assertions.assertEquals("kathy", rs.getString("name"));
            Assertions.assertNotEquals("monica", rs.getString("name"));

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_UpdateRowWithId4FromTablePeople: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }
}
