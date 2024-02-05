package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.Profile;

public interface IProfileRepository extends IRepository<Profile> {
    Response exists(Profile profile);
}
