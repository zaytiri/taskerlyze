package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

public enum Order {
    ASCENDING("ASC"),
    DESCENDING("DESC");
    public final String value;

    Order(String value) {
        this.value = value;
    }
}