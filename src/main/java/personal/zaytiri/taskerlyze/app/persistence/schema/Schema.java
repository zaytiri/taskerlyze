package personal.zaytiri.taskerlyze.app.persistence.schema;

import com.github.underscore.U;
import org.json.JSONArray;
import org.json.JSONObject;
import personal.zaytiri.taskerlyze.app.presentation.Main;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Database;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Schema {

    private static Database SCHEMA;

    public Schema() {
    }

    public static Database getSchema() {
        if (SCHEMA != null) {
            return SCHEMA;
        }

        JSONObject json = null;
        try {
            json = new JSONObject(U.xmlToJson(convertXmlFileToString()));
        } catch (FileNotFoundException e) {
            System.err.println("ERR: Database schema xml file was not found.");
            return null;
        }

        Database db = new Database();
        JSONObject database = json.getJSONObject("database");
        String dbName = database.getString("name");
        db.setName(dbName);

        Table table = new Table();
        List<Table> tables = new ArrayList<>();
        List<Column> columns = new ArrayList<>();

        JSONArray ts = database.getJSONArray("table");
        for (Object t : ts) {
            table.setName(((JSONObject) t).getString("name"));

            JSONArray cs = ((JSONObject) t).getJSONArray("column");

            for (Object c : cs) {
                Column column = new Column();
                column.setName(((JSONObject) c).getString("name"));
                column.setType(((JSONObject) c).getString("type"));
                column.setDefaultValue(((JSONObject) c).getString("default"));
                column.setIsPrimaryKey(((JSONObject) c).getString("isprimarykey"));
                column.setTableName(((JSONObject) t).getString("name"));
                columns.add(column);
            }
            table.setColumns(columns);
            tables.add(table);
            table = new Table();
            columns = new ArrayList<>();
        }

        db.setTables(tables);
        SCHEMA = db;

        return SCHEMA;
    }

    private static String convertXmlFileToString() throws FileNotFoundException {
        InputStream xmlFile = Main.class.getClassLoader().getResourceAsStream("database/database.xml"); // todo: name must be given as parameter
        if (xmlFile == null) {
            throw new FileNotFoundException("");
        }

        InputStreamReader streamReader = new InputStreamReader(xmlFile, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(streamReader);
        String line;
        StringBuilder xml = new StringBuilder();

        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            xml.append(line.trim());
        }

        return xml.toString();
    }
}
