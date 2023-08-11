package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema;

public class Column {
    private String name;
    private String type;
    private String defaultValue;
    private boolean isPrimaryKey;
    private String tableName;

    public Column(String name, String type, String defaultValue, boolean isPrimaryKey, String tableName) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.isPrimaryKey = isPrimaryKey;
        this.tableName = tableName;
    }

    public Column() {
    }

    /**
     * Gets the default value of a column and returns that value as the given <T> Type.
     *
     * @param tClass the class of the type of the default column value. e.g. String.class
     * @param <T>    optional type class.
     * @returns the default value of a column in the desired type.
     */
    public <T> T getDefaultValue(Class<T> tClass) {
        return tClass.cast(defaultValue);
    }

    public boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(boolean isPrimaryKey) {
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
    public void setDefaultValue(int defaultValue) {
        this.defaultValue = String.valueOf(defaultValue);
    }
}
