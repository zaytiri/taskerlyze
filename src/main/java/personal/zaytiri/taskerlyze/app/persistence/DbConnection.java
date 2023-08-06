package personal.zaytiri.taskerlyze.app.persistence;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.CreateTableQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Database;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Schema;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection INSTANCE;
    private final Database schema;
    private String path;
    private Connection connection;

    private DbConnection(String fileName) {
        connection = null;
        schema = Schema.getSchema(fileName);
        path = "";
        if (schema == null) {
            System.err.println("Schema not correctly configured.");
            return;
        }

        path = getDbConnectionPath() + "database\\" + schema.getName() + ".db";
        createDatabase();
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

    public static DbConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbConnection("database");
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

    public void resetInstance() {
        INSTANCE = null;
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
