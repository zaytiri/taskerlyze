package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;

public interface IGenericClauses<QB extends QueryBuilder> {
    QB and();

    QB and(Object value);

    QB between(Object value);

    QB or();

    QB where(Column leftColumn, Operators operator, Object rightColumn);

    QB where(Column leftColumn, Operators operator, Column rightColumn);

    QB where(Column leftColumn, Operators operator);

    QB where(Column leftColumn);
}
