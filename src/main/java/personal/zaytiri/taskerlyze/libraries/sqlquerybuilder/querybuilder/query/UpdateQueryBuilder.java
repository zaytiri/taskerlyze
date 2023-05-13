package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;
import java.util.Map;

public class UpdateQueryBuilder extends QueryBuilder {

    public UpdateQueryBuilder(Connection connection) {
        super(connection);
    }

    public UpdateQueryBuilder update(Table table) {
        query.append("update ").append(table.getName());
        return this;
    }

    public UpdateQueryBuilder values(Map<Column, Object> sets) {
        query.append(" set ");

        boolean first = true;
        for (Map.Entry<Column, Object> entry : sets.entrySet()) {
            if (first) {

                query.append(getColumnWithTableAbbreviation(entry.getKey())).append("=? ");
                first = false;
            }
            query.append(", ").append(entry.getKey()).append("=? ");
            values.add(entry.getValue());
        }

        return this;
    }
}
