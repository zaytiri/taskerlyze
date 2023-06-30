package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;

public class CreateTableQueryBuilder extends QueryBuilder {


    public CreateTableQueryBuilder(Connection connection) {
        super(connection);
    }

    /**
     * Generates an SQL query for creating a given table with all associated columns.
     *
     * @param table containing all information about the table and respective columns.
     */
    public void create(Table table) {
        appendKeyword(Clause.CREATE.value);
        appendKeyword(Clause.TABLE.value);
        appendKeyword(Clause.IF_NOT_EXISTS.value);
        query.append(table.getName());
        query.append(" ( ");

        String primaryKey = "";

        for (Column col : table.getColumns()) {
            query.append(col.getName()).append(" ");
            query.append(col.getType());

            if (!col.getDefaultValue(String.class).equalsIgnoreCase(Operators.NULL.value)) {
                appendKeyword(Operators.NOT_NULL.value);
            }

            if (col.getIsPrimaryKey()) {
                primaryKey = col.getName();
            }

            query.append(", ");
        }

        query.append("primary key ( ").append(primaryKey).append(" ) )");
    }
}
