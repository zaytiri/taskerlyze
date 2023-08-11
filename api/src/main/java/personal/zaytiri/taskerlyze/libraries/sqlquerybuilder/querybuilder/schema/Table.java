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

    /**
     * Gets all columns except the columns which the names are contained inside the exclude parameter.
     *
     * @param exclude: a list of names to exclude.
     * @returns a list of columns.
     */
    public List<Column> getAllColumnsExcept(List<String> exclude) {
        List<Column> columnsToReturn = new ArrayList<>();

        for (Column col : columns) {
            if (exclude.contains(col.getName())) {
                continue;
            }
            columnsToReturn.add(col);
        }
        return columnsToReturn;
    }

    /**
     * Gets a Column object depending on a given name.
     *
     * @param name: column's name.
     * @returns a Column object.
     */
    public Column getColumn(String name) {
        for (Column col : columns) {
            if (col.getName().equals(name)) {
                return col;
            }
        }
        return null;
    }

    /**
     * Gets all columns which the name is contained in a given list of names.
     *
     * @param names: a list of names containing all the columns to be returned.
     * @returns a list of columns.
     */
    public List<Column> getColumns(List<String> names) {
        List<Column> columnsToReturn = new ArrayList<>();

        for (Column col : columns) {
            if (names.contains(col.getName())) {
                columnsToReturn.add(col);
            }
        }
        return columnsToReturn;
    }

    /**
     * Gets all existing columns in a table.
     *
     * @returns a list of columns.
     */
    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
