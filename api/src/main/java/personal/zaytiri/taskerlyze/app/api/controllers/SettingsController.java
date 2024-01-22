package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Settings;
import personal.zaytiri.taskerlyze.app.persistence.DbConnection;

public class SettingsController extends Controller<Settings> {

    public OperationResult<Settings> resetDatabase() {

        boolean isDeleted = DbConnection.getInstance().deleteData();
        if (isDeleted) {
            DbConnection.getInstance().createDatabase();
        }

        MessageResult message = new MessageResult();
        if (!isDeleted) {
            message.setCode(CodeResult.NOT_DELETED);
        } else {
            message.setCode(CodeResult.DELETED);
        }

        return new OperationResult<>(isDeleted, message, null);
    }

    @Override
    protected Settings getEntityInstance(int id) {
        return new Settings().getInstance().setId(id);
    }

    @Override
    protected Settings getEntityInstance() {
        return new Settings().getInstance();
    }
}
