package libraries.sqlquerybuilder;

import libraries.sqlquerybuilder.helpers.DatabaseTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.CreateTableQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateTableQueryBuilderTests {
    private final static Logger LOGGER = Logger.getLogger(CreateTableQueryBuilderTests.class.getName());

    @BeforeAll
    public static void createDatabaseSchema() {
        DatabaseTestHelper.createDatabaseSchema();
    }

    @AfterAll
    public static void deleteDatabase() {
        DatabaseTestHelper.deleteDatabase();
    }

    @ParameterizedTest
    @ValueSource(strings = {"tasks", "people"})
    void Should_CreateTableInDatabase(String tableName) {
        Connection connection = null;
        try {
            // arrange
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseTestHelper.getDbConnectionPath());
            Table table = DatabaseTestHelper.getSchema().getTable(tableName);

            // act
            CreateTableQueryBuilder query = new CreateTableQueryBuilder(connection);
            query.setCloseConnection(false);
            query.create(table);
            Response response = query.execute();
            LOGGER.log(Level.INFO, response.getQueryExecuted());

            String tableExists = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(tableExists);
            rs.next();

            String result = rs.getString("name");

            statement.close();
            connection.close();

            // assert
            Assertions.assertTrue(response.isSuccess());
            Assertions.assertEquals(tableName, result);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Should_CreateTableInDatabase: " + e.getMessage(), e);
        } finally {
            DatabaseTestHelper.closeConnection(connection);
        }
    }
}
