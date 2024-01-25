package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.domain.Profile;

public class ProfileController extends Controller<Profile> {

    @Override
    protected Profile getEntityInstance(int id) {
        return new Profile().getInstance().setId(id);
    }

    @Override
    protected Profile getEntityInstance() {
        return new Profile().getInstance();
    }
}
