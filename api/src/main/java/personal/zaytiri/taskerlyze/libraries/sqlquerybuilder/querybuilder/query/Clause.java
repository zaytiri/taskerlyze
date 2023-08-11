package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

public enum Clause {
    SELECT("SELECT"),
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    CREATE("CREATE"),
    TABLE("TABLE"),
    IF_EXISTS("IF EXISTS"),
    IF_NOT_EXISTS("IF NOT EXISTS"),
    FROM("FROM"),
    INTO("INTO"),
    VALUES("VALUES"),
    WHERE("WHERE"),
    JOIN("JOIN"),
    ORDER_BY("ORDER BY"),
    GROUP_BY("GROUP BY"),
    LIMIT("LIMIT"),
    OFFSET("OFFSET"),
    AND("AND"),
    OR("OR"),
    ON("ON"),
    SET("SET");

    public final String value;

    Clause(String value) {
        this.value = value;
    }
}