package personal.zaytiri.taskerlyze.app.persistence;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Database;
import personal.zaytiri.taskerlyze.app.persistence.schema.Schema;

import java.nio.file.Path;
import java.sql.*;

public class DbConnection {

    private static DbConnection INSTANCE;
    private static DbConnection CUSTOM_INSTANCE;

    private final String path;
    private Connection connection;
    private final Database schema;

    public Database getSchema() {
        return schema;
    }

    public Connection getConnection(){
        return connection;
    }

    private DbConnection() {
        connection = null;
        schema = Schema.getSchema();
        path = getDbConnectionPath() + "database\\taskerlyze.db";
    }
    public static DbConnection getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DbConnection();
        }

        return INSTANCE;
    }

    private DbConnection(String fileName) {
        connection = null;
        schema = Schema.getSchema();
        path = getDbConnectionPath() + "database\\" + fileName + ".db";

    }
    public static DbConnection getCustomInstance(String fileName) {
        if(CUSTOM_INSTANCE == null) {
            CUSTOM_INSTANCE = new DbConnection(fileName);
        }
        return CUSTOM_INSTANCE;
    }

    private String getDbConnectionPath(){
        String currentDirectory = Path.of("").toAbsolutePath().toString();
        return currentDirectory + "\\src\\main\\resources\\";
    }

    public Connection open(){
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

        return connection;
    }

    public void close(){
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(SQLException e)
        {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }
}
