package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema;

import java.util.List;

public class Database {
    private String name;
    private List<Table> tables;

    public Database(String name, List<Table> tables) {
        this.name = name;
        this.tables = tables;
    }

    public Database() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table getTable(String name) {
        for (Table tb : tables) {
            if (tb.getName().equals(name)) {
                return tb;
            }
        }
        return null;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
