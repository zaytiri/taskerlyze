package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;
import java.util.List;

public class InsertQueryBuilder extends QueryBuilder {

    // todo: insert variable for last inserted object or last inserted id.

    // todo: implement an automatically way to auto increment an id when inserting something.
    public InsertQueryBuilder(Connection connection) {
        super(connection);
    }

    public InsertQueryBuilder insertInto(Table table) {
        query.append("insert into ").append(table.getName());
        return this;
    }

    public InsertQueryBuilder insertInto(Table table, List<Column> columns) {
        insertInto(table);

        query.append(" (");
        query.append(getMultipleColumnsNameByComma(columns, false));
        query.append(")");
        return this;
    }

    public InsertQueryBuilder values(Object... values) {
        this.values.addAll(List.of(values));

        query.append(" values (?");
        query.append(",?".repeat(values.length - 1));
        query.append(")");
        return this;
    }
}
