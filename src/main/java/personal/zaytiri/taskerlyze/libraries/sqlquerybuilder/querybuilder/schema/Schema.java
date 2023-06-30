package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema;

import com.github.underscore.U;
import org.json.JSONArray;
import org.json.JSONObject;
import personal.zaytiri.taskerlyze.app.presentation.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Schema {

    private static Database SCHEMA;

    private Schema() {
    }

    /**
     * If schema is NULL, then it will read all properties from the user-created xml file with a given fileName and maps them to Database, Table and Column objects.
     *
     * @param fileName: the name of the xml file to be read.
     * @returns a Database object containing every information about the schema depending on xml created properties.
     */
    public static Database getSchema(String fileName) {
        if (SCHEMA != null) {
            return SCHEMA;
        }

        JSONObject json = null;
        try {
            json = new JSONObject(U.xmlToJson(convertXmlFileToString(fileName)));
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

    private static String convertXmlFileToString(String fileName) throws FileNotFoundException {
        InputStream xmlFile = Main.class.getClassLoader().getResourceAsStream("database/" + fileName + ".xml");
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
                return "";
            }
            xml.append(line.trim());
        }

        return xml.toString();
    }
}
