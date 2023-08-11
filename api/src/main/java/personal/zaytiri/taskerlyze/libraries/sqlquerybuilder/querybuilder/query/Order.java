package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

public enum Order {
    ASCENDING("ASC"),
    DESCENDING("DESC");
    public final String value;

    Order(String value) {
        this.value = value;
    }

    public static Order get(String text) {
        if (text.equalsIgnoreCase("asc") || text.equalsIgnoreCase("ascending")) {
            return ASCENDING;
        } else if (text.equalsIgnoreCase("desc") || text.equalsIgnoreCase("descending") || text.equalsIgnoreCase("dsc")) {
            return DESCENDING;
        } else {
            throw new IllegalArgumentException("No constant with value " + text + " or similar found.");
        }
    }
}