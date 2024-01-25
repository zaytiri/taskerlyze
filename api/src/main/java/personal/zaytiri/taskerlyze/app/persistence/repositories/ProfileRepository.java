package personal.zaytiri.taskerlyze.app.persistence.repositories;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.Profile;
import personal.zaytiri.taskerlyze.app.persistence.mappers.ProfileMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.ProfileModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.IProfileRepository;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.Operators;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.SelectQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

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
