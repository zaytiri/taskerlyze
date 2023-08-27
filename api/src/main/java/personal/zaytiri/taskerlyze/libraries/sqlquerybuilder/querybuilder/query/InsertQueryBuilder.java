package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;
import java.util.List;

public class InsertQueryBuilder extends QueryBuilder {

    public InsertQueryBuilder(Connection connection) {
        super(connection);
    }

    /**
     * Generates a partial SQL query to insert data into a given table.
     * This method should be the entry point of the SQL insert query.
     * All columns of a table will be expected to be inserted into.
     * Needs to be followed up by values() method.
     *
     * @param table to insert data into.
     * @returns InsertQueryBuilder
     */
    public InsertQueryBuilder insertInto(Table table) {
        resetQuery();
        tryAppendKeyword(Clause.INSERT.value);
        tryAppendKeyword(Clause.INTO.value);
        query.append(table.getName());
        return this;
    }

    /**
     * Generates a partial SQL query to insert specific data into a given table.
     * This method should be the entry point of the SQL insert query.
     * Also receives a list of columns which define the specific columns the data will be inserted into.
     * Needs to be followed up by values() method.
     *
     * @param table   to insert data into.
     * @param columns a list of columns to be inserted.
     * @returns InsertQueryBuilder
     */
    public InsertQueryBuilder insertInto(Table table, List<Column> columns) {
        resetQuery();
        insertInto(table);

        query.append(" (");
        query.append(getMultipleColumnsNameByComma(columns));
        query.append(")");
        return this;
    }

    /**
     * Generates a partial SQL query to insert specific values into a table.
     * The quantity of values should be the same as the number of columns defined in the previous method.
     * If insertInto(table) was used, the number of values should be equal to all table's columns and in the correct order of the table's columns' list.
     *
     * @param values to be inserted.
     * @returns InsertQueryBuilder
     */
    public InsertQueryBuilder values(Object... values) {
        this.values.addAll(List.of(values));

        appendKeyword(Clause.VALUES.value);
        query.append("(?");
        query.append(",?".repeat(values.length - 1));
        query.append(")");
        return this;
    }

    /**
     * Generates a partial SQL query to insert specific values into a table.
     * The quantity of values should be the same as the number of columns defined in the previous method.
     * If insertInto(table) was used, the number of values should be equal to all table's columns and in the correct order of the table's columns' list.
     *
     * @param values to be inserted.
     * @returns InsertQueryBuilder
     */
    public InsertQueryBuilder values(List<Pair<String, Object>> values) {
        for (Pair<String, Object> entry : values) {
            this.values.add(entry.getValue());
        }

        appendKeyword(Clause.VALUES.value);
        query.append("(?");
        query.append(",?".repeat(values.size() - 1));
        query.append(")");
        return this;
    }
}
