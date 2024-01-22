package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.domain.Settings;

public class SettingsController extends Controller<Settings> {

    @Override
    protected Settings getEntityInstance(int id) {
        return new Settings().getInstance().setId(id);
    }

    @Override
    protected Settings getEntityInstance() {
        return new Settings().getInstance();
    }
}
