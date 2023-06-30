package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;
import java.util.Map;

public class UpdateQueryBuilder extends QueryBuilder {

    public UpdateQueryBuilder(Connection connection) {
        super(connection);
    }

    /**
     * Generates a partial SQL query to set a value to a specific column.
     * It is an alternative to values() method.
     * This method will set only 1 value at a time, and it could be used multiple times. But in this case it's better to use values() method.
     *
     * @param column   to be set a new value.
     * @param newValue to be updated in a column.
     * @returns UpdateQueryBuilder
     */
    public UpdateQueryBuilder set(Column column, Object newValue) {
        if (!tryAppendKeyword(Clause.SET.value)) {
            query.append(", ");
        }

        query.append(column.getName()).append("=? ");
        values.add(newValue);
        return this;
    }

    /**
     * Generates a partial SQL query to update a table's row/rows.
     * This method should be the entry point of the SQL update query.
     * It should be followed up by set() OR values() method.
     *
     * @param table to be updated.
     * @return
     */
    public UpdateQueryBuilder update(Table table) {
        tryAppendKeyword(Clause.UPDATE.value);
        query.append(table.getName());
        return this;
    }

    /**
     * Generates a partial SQL query to set multiple values to their respective column.
     * It is an alternative to set() method.
     * It will generate the query iterating through every column-pair defined. If only 1 value is needed, it's better to use set() method.
     *
     * @param sets which are multiple column-value pairs to be updated.
     * @returns UpdateQueryBuilder
     */
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
