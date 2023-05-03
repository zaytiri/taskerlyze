package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Table;

public class DeleteQueryBuilder extends QueryBuilder{

    public DeleteQueryBuilder() {
        super();
    }

    public DeleteQueryBuilder deleteFrom(Table table){
        query.append("delete from ").append(table.getName());
        return this;
    }
}
