package app.api.helpers;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiTestHelper {
    private final static Logger LOGGER = Logger.getLogger(ApiTestHelper.class.getName());

    public static long getDate(int calendarParcel, int minutesToAdd) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(calendarParcel, minutesToAdd);

        return cal.getTime().getTime();
    }

    public static String getFormattedInsertQuery(String table, List<String> columns, List<Object> values) {
        StringBuilder query = new StringBuilder();

        query.append("insert into ").append(table).append(" (");

        boolean useComma = false;
        for (String column : columns) {
            if (useComma) {
                query.append(", ");
            }
            query.append(column);
            useComma = true;
        }

        query.append(") values ( ");

        useComma = false;
        for (Object value : values) {
            if (useComma) {
                query.append(", ");
            }
            if (value instanceof String)
                query.append("'").append(value).append("'");
            else {
                query.append(value);
            }
            useComma = true;
        }

        query.append(")");

        return query.toString();
    }

    public static String getInsertQueryForCategoriesTable(List<Object> values) {
        List<String> columns = new ArrayList<>() {{
            add("name");
            add("updated_at");
            add("created_at");
        }};

        return ApiTestHelper.getFormattedInsertQuery("categories", columns, values);
    }

    public static String getInsertQueryForTasksTable(List<Object> values) {
        List<String> columns = new ArrayList<>() {{
            add("name");
            add("is_done");
            add("description");
            add("category_id");
            add("updated_at");
            add("created_at");
        }};

        return ApiTestHelper.getFormattedInsertQuery("tasks", columns, values);
    }

    public static void insertMockData(List<String> queries) {
        Connection connection = null;
        try {
            // create a database connection
            String currentDirectory = Path.of("").toAbsolutePath().toString();
            connection = DriverManager.getConnection("jdbc:sqlite:" + currentDirectory + "\\src\\main\\resources\\database\\taskerlyze.db");

            Statement statement = connection.createStatement();
            for (String query : queries) {
                statement.executeUpdate(query);
            }
            statement.close();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "insertMockData: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
