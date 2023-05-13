package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

public enum Order {
    ASCENDING(" asc "),
    DESCENDING(" desc ");
    public final String value;

    Order(String value) {
        this.value = value;
    }
}