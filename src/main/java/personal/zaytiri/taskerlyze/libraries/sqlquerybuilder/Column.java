package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder;

public class Column {
    private String name;
    private String type;
    private String defaultValue;

    public Column(String name, String type, String defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public Column() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public <T> T getDefaultValue(Class<T> tClass) {
        return tClass.cast(defaultValue);
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
