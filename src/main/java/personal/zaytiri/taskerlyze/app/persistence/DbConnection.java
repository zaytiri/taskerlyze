package personal.zaytiri.taskerlyze.app.persistence;

import personal.zaytiri.taskerlyze.app.persistence.schema.Schema;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.CreateTableQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Database;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection INSTANCE;
    private static DbConnection CUSTOM_INSTANCE;

    private final String path;
    private final Database schema;

    private Connection connection;

    private DbConnection() {
        connection = null;
        schema = Schema.getSchema();
        // todo: think of a way to maybe pass the db name defined inside the xml file considering I need to have a way to test with a mock database...
        // ...while having a different xml file just for testing?
        path = getDbConnectionPath() + "database\\taskerlyze.db";
    }

    private DbConnection(String fileName) {
        connection = null;
        schema = Schema.getSchema();
        path = getDbConnectionPath() + "database\\" + fileName + ".db";

    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DbConnection getCustomInstance(String fileName) {
        if (CUSTOM_INSTANCE == null) {
            CUSTOM_INSTANCE = new DbConnection(fileName);
        }
        return CUSTOM_INSTANCE;
    }

    public static DbConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbConnection();
        }

        return INSTANCE;
    }

    public Database getSchema() {
        return schema;
    }

    public Connection open() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

        return connection;
    }

    private void createDatabase() {
        CreateTableQueryBuilder query = new CreateTableQueryBuilder(open());

        for (Table tb : schema.getTables()) {
            query.create(tb);
            query.execute();
        }
    }

    private String getDbConnectionPath() {
        String currentDirectory = Path.of("").toAbsolutePath().toString();
        return currentDirectory + "\\src\\main\\resources\\";
    }
}
