package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

public interface IRepository<GEntity> {

    Response create(GEntity entity);

    Response delete(GEntity entity);

    Response read(GEntity entity);

    Response readAll();

    Response update(GEntity entity);
}
