package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;

    private List<Column> columns;

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    public Table() {
    }

    public Column getColumn(String name) {
        for (Column col : columns) {
            if (col.getName().equals(name)) {
                return col;
            }
        }
        return null;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns(List<String> names) {
        List<Column> columnsToReturn = new ArrayList<>();

        for (Column col : columns) {
            if (names.contains(col.getName())) {
                columnsToReturn.add(col);
            }
        }
        return columnsToReturn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
