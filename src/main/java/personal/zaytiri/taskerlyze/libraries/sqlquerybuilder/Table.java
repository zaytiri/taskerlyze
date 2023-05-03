package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder;

import java.util.List;

public class Table {
    private String name;

    private List<Column> columns;

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    public Table(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Column getColumn(String name){
        for (Column col : columns) {
            if(col.getName().equals(name)){
                return col;
            }
        }
        return null;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
