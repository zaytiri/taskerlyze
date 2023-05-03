package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Table;

import java.util.Map;

public class UpdateQueryBuilder extends QueryBuilder{

    public UpdateQueryBuilder() {
        super();
    }

    public UpdateQueryBuilder update(Table table){
        query.append("update ").append(table.getName());
        return this;
    }

    public UpdateQueryBuilder values(Map<Column, Object> sets){
        query.append(" set ");

        boolean first = true;
        for (Map.Entry<Column, Object> entry : sets.entrySet()) {
            if (first){
                query.append(entry.getKey().getName()).append("=? ");
                first = false;
            }
            query.append("and ").append(entry.getKey()).append("=? ");
            values.add(entry.getValue());
        }

        return this;
    }
}
