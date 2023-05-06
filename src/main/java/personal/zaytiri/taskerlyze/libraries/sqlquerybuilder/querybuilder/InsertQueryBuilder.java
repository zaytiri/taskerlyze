package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Table;

import java.sql.Connection;
import java.util.List;

public class InsertQueryBuilder extends QueryBuilder{

    // todo insert variable for last inserted object or last inserted id
    public InsertQueryBuilder(Connection connection) {
        super(connection);
    }

    public InsertQueryBuilder insertInto(Table table){
        query.append("insert into ").append(table.getName());
        return this;
    }

    public InsertQueryBuilder insertInto(Table table, List<Column> columns){
        insertInto(table);

        query.append(" (");
        appendMultipleColumnsNameByComma(columns);
        query.append(")");
        return this;
    }

    public InsertQueryBuilder values(Object... values){
        this.values.addAll(List.of(values));

        query.append(" values (?");
        for (int i = 0; i < values.length; i++) {
            query.append(",?");
        }
        query.append(")");
        return this;
    }
}
