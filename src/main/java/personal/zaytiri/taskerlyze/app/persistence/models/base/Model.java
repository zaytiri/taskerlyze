package personal.zaytiri.taskerlyze.app.persistence.models.base;

import personal.zaytiri.taskerlyze.app.persistence.schema.Schema;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Database;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.util.Date;

public abstract class Model {

    protected Table table;
    protected int id;
    protected String name;
    protected Date updatedAt;
    private Date createdAt;

    protected Model(String tableName) {
        Database schema = Schema.getSchema();
        if (schema == null) {
            System.err.println("No table was found in schema with the following name: " + tableName);
            return;
        }
        this.table = schema.getTable(tableName);

        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    protected void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public Model setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Model setName(String name) {
        this.name = name;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    protected void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public abstract Object[] getValues();
}
