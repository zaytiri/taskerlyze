package libraries.sqlquerybuilder.helpers;


import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.schema.Database;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.schema.Table;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseTestHelper {
    private final static Logger LOGGER = Logger.getLogger(DatabaseTestHelper.class.getName());
    private static Database schema;

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void createDatabaseSchema() {
        // table tasks
        Table tasks = new Table();
        tasks.setName("tasks");

        Column id = new Column("id", "integer", "1", true, tasks.getName());
        Column name = new Column("name", "varchar(255)", "null", false, tasks.getName());
        List<Column> tasksColumns = new ArrayList<>();
        tasksColumns.add(id);
        tasksColumns.add(name);

        tasks.setColumns(tasksColumns);

        // table people
        Table people = new Table();
        people.setName("people");

        id = new Column("id", "integer", "1", true, people.getName());
        name = new Column("name", "varchar(255)", "null", false, people.getName());
        List<Column> peopleColumns = new ArrayList<>();
        peopleColumns.add(id);
        peopleColumns.add(name);

        people.setColumns(peopleColumns);

        // intermediate table tasks_people
        Table tasksPeople = new Table();
        tasksPeople.setName("tasks_people");

        id = new Column("id", "integer", "1", true, tasksPeople.getName());
        Column tasksId = new Column("tasks_id", "integer", "1", false, tasksPeople.getName());
        Column peopleId = new Column("people_id", "integer", "1", false, tasksPeople.getName());
        List<Column> tasksPeopleColumns = new ArrayList<>();
        tasksPeopleColumns.add(id);
        tasksPeopleColumns.add(tasksId);
        tasksPeopleColumns.add(peopleId);

        tasksPeople.setColumns(tasksPeopleColumns);

        // add tables to database
        List<Table> tables = new ArrayList<>();
        tables.add(tasks);
        tables.add(people);
        tables.add(tasksPeople);

        schema = new Database("test_db", tables);
    }

    public static void createTables() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + getDbConnectionPath());

            for (Table table : schema.getTables()) {
                String query = createTableQuery(table);
                Statement statement = connection.createStatement();

                statement.executeUpdate("drop table if exists " + table.getName());

                statement.executeUpdate(query);
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createTables: " + e.getMessage(), e);
        } finally {
            closeConnection(connection);
        }
    }

    public static void deleteDatabase() {
        try {
            Files.delete(Path.of(getDbConnectionPath()));
        } catch (Exception e) {
            // if any error occurs
            LOGGER.log(Level.SEVERE, "deleteDatabase: " + e.getMessage(), e);
        }
    }

    public static String getDbConnectionPath() {
        String currentDirectory = Path.of("").toAbsolutePath().toString();
        String databaseName = "sqlquerybuilder_test.db";
        return currentDirectory + "\\src\\test\\resources\\libraries\\sqlquerybuilder\\" + databaseName;
    }

    public static int getRowCount(Connection connection, String query) {
        Statement statement = null;
        int rowCount = 0;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                rowCount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rowCount;
    }

    public static Database getSchema() {
        return schema;
    }

    public static void insertMockData() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + getDbConnectionPath());

            List<String> queries = new ArrayList<>() {
                {
                    add("insert into people (id, name) values (1, 'cat')");
                    add("insert into people (id, name) values (2, 'nuno')");
                    add("insert into people (id, name) values (3, 'pedro')");
                    add("insert into people (id, name) values (4, 'monica')");
                    add("insert into people (id, name) values (5, null)");
                    add("insert into people (id, name) values (6, 'oni')");

                    add("insert into tasks (id, name) values (1, 'do something weird')");
                    add("insert into tasks (id, name) values (2, 'dont do that')");
                    add("insert into tasks (id, name) values (3, 'do anything spectacular')");
                    add("insert into tasks (id, name) values (4, 'do nothing')");

                    add("insert into tasks_people (id, tasks_id, people_id) values (1, 1, 1)");
                    add("insert into tasks_people (id, tasks_id, people_id) values (2, 2, 1)");
                    add("insert into tasks_people (id, tasks_id, people_id) values (3, 3, 4)");
                }
            };

            Statement statement = connection.createStatement();
            for (String query : queries) {
                statement.executeUpdate(query);
            }
            statement.close();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "insertMockData: " + e.getMessage(), e);
        } finally {
            closeConnection(connection);
        }
    }

    private static String createTableQuery(Table table) {
        StringBuilder query = new StringBuilder();
        query.append("create table ").append(table.getName()).append(" ( ");
        String primaryKey = "";
        for (Column col : table.getColumns()) {
            query.append(col.getName()).append(" ");
            query.append(col.getType());
            if (!col.getDefaultValue(String.class).equalsIgnoreCase("null")) {
                query.append(" not null");
            }
            if (col.getIsPrimaryKey()) {
                primaryKey = col.getName();
            }
            query.append(", ");
        }
        query.append("primary key ( ").append(primaryKey).append(" ) )");

        return query.toString();
    }
}
