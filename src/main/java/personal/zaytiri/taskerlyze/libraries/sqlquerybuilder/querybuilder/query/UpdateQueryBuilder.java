package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;
import java.util.Map;

public class UpdateQueryBuilder extends QueryBuilder {

    public UpdateQueryBuilder(Connection connection) {
        super(connection);
    }

    public UpdateQueryBuilder set(Column column, Object newValue) {
        if (!tryAppendKeyword(Clause.SET.value)) {
            query.append(", ");
        }

        query.append(column.getName()).append("=? ");
        values.add(newValue);
        return this;
    }

    public UpdateQueryBuilder update(Table table) {
        tryAppendKeyword(Clause.UPDATE.value);
        query.append(table.getName());
        return this;
    }

    public UpdateQueryBuilder values(Map<Column, Object> sets) {
        if (!tryAppendKeyword(Clause.SET.value)) {
            return this;
        }

        boolean comma = false;
        for (Map.Entry<Column, Object> entry : sets.entrySet()) {
            if (comma) {
                query.append(", ");
            }

            query.append(entry.getKey().getName()).append("=? ");
            values.add(entry.getValue());
            comma = true;
        }

        return this;
    }
}
