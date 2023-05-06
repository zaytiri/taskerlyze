package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Table;

import java.sql.Connection;

public class CreateTableQueryBuilder extends QueryBuilder {


    public CreateTableQueryBuilder(Connection connection) {
        super(connection);
    }

    public void create(Table table) {
        query.append("create table ");
        query.append("if not exists ");
        query.append(table.getName());
        query.append(" ( ");

        String primaryKey = "";

        for (Column col : table.getColumns()) {
            query.append(col.getName()).append(" ");
            query.append(col.getType()).append(" ");

            if (!col.getDefaultValue(String.class).equalsIgnoreCase("null")) {
                query.append("not null, ");
            }

            if (col.getIsPrimaryKey()) {
                primaryKey = col.getName();
            }
        }

        query.append("primary key ( ").append(primaryKey).append(" ) )");
    }
}
