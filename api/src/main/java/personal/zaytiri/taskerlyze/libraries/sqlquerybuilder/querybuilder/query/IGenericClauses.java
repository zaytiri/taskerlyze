package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;

public interface IGenericClauses<QB extends QueryBuilder> {
    /**
     * Adds the AND clause to the query considering the number of conditions that needs to be connected to this clause.
     * E.g. ... AND (condition1 AND condition2) ... => and(2).where(condition1).and().where(condition2);
     *
     * @return QB specific class
     */
    QB and(int numberOfConditions);

    /**
     * Adds the AND clause to the query.
     *
     * @return QB specific class
     */
    QB and();

    /**
     * Adds the AND clause to the query to compare with a given value. Must be used with the BETWEEN clause.
     *
     * @param value to compare between another value
     * @return QB specific class
     */
    QB and(Object value);

    /**
     * Adds the BETWEEN clause to the query.
     *
     * @param value to be compared between another value
     * @return QB specific class
     */
    QB between(Object value);

    /**
     * Adds the OR clause to the query considering the number of conditions that needs to be connected to this clause.
     * * E.g. ... OR (condition1 OR condition2) ... => or(2).where(condition1).or().where(condition2);
     *
     * @return QB specific class
     */
    QB or(int numberOfConditions);

    /**
     * Adds the OR clause to the query.
     *
     * @return QB specific class
     */
    QB or();

    /**
     * Adds the WHERE clause to the query and limits the query to search for where the LEFT COLUMN <!OPERATOR!> RIGHT VALUE.
     * The LEFT COLUMN should be always of type column.
     * When using the LIKE operator, only the needed sub-string should be used. There is no need to add the % between the sub-string.
     *
     * @param leftColumn  to limit the query
     * @param operator
     * @param rightColumn to limit the query and could be any value
     * @return QB specific class
     */
    QB where(Column leftColumn, Operators operator, Object rightColumn);

    /**
     * Adds the WHERE clause to the query and limits the query to search for where the LEFT COLUMN <!OPERATOR!> RIGHT COLUMN.
     * The LEFT COLUMN should be always of type column.
     *
     * @param leftColumn  to limit the query
     * @param operator
     * @param rightColumn to limit the query and must be of type Column
     * @return QB specific class
     */
    QB where(Column leftColumn, Operators operator, Column rightColumn);

    /**
     * Adds the WHERE clause to the query and limits the query to search for where the LEFT COLUMN <!OPERATOR!>.
     * The LEFT COLUMN should be always of type column.
     * The OPERATOR should be a standalone one, e.g. IS NULL or IS NOT NULL. (standalone meaning that does not need a second value after the operator)
     *
     * @param leftColumn to limit the query
     * @param operator
     * @return QB specific class
     */
    QB where(Column leftColumn, Operators operator);

    /**
     * Adds the WHERE clause to the query with a given column name.
     * The LEFT COLUMN should be always of type column.
     * This must be used with a second clause, e.g. BETWEEN clause.
     *
     * @param leftColumn to limit the query
     * @return QB specific class
     */
    QB where(Column leftColumn);
}
