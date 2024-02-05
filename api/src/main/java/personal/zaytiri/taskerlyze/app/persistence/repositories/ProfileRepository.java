package personal.zaytiri.taskerlyze.app.persistence.repositories;

import jakarta.inject.Inject;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.query.builders.SelectQueryBuilder;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.query.enums.Operators;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.Profile;
import personal.zaytiri.taskerlyze.app.persistence.mappers.ProfileMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.ProfileModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.IProfileRepository;

import java.util.ArrayList;
import java.util.List;

public class ProfileRepository extends Repository<Profile, ProfileModel, ProfileMapper> implements IProfileRepository {

    @Inject
    public ProfileRepository() {
        super(new ProfileModel(), new ProfileMapper());
    }

    @Override
    public Response exists(Profile profile) {
        model = mapper.toModel(profile);

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        Column name = model.getTable().getColumn("name");
        List<Column> columnsToSelect = new ArrayList<>();
        columnsToSelect.add(name);

        query.select(columnsToSelect)
                .from(model.getTable())
                .where(name, Operators.EQUALS, model.getName());

        return query.execute();
    }
}
