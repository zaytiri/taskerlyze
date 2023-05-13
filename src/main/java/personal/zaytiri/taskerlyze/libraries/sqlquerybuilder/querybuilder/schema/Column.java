package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema;

public class Column {
    private String name;
    private String type;
    private String defaultValue;
    private String isPrimaryKey;
    private String tableName;

    public Column(String name, String type, String defaultValue, String isPrimaryKey, String tableName) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.isPrimaryKey = isPrimaryKey;
        this.tableName = tableName;
    }

    public Column() {
    }

    public <T> T getDefaultValue(Class<T> tClass) {
        return tClass.cast(defaultValue);
    }

    public boolean getIsPrimaryKey() {
        return Boolean.parseBoolean(isPrimaryKey);
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
