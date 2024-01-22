package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;

public class SettingsController extends Controller<SubTask> {

    @Override
    protected SubTask getEntityInstance(int id) {
        return new SubTask().getInstance().setId(id);
    }

    @Override
    protected SubTask getEntityInstance() {
        return new SubTask().getInstance();
    }
}
