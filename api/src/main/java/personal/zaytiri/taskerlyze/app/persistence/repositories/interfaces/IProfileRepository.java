package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.api.domain.Profile;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

public interface IProfileRepository extends IRepository<Profile> {
    Response exists(Profile profile);
}
