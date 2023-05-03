package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder;

public enum Order{
    ASCENDING(" asc "),
    DESCENDING(" desc ");
    public final String value;

    private Order(String value) {
        this.value = value;
    }
}