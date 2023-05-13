package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

public enum Operators {
    EQUALS("="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_OR_EQUAL_THAN(">="),
    LESS_OR_EQUAL_THAN("<="),
    IN(" in "),
    NOT_IN(" not in "),
    LIKE(" like "),
    IS_NULL("is null");

    public final String value;

    Operators(String value) {
        this.value = value;
    }
}