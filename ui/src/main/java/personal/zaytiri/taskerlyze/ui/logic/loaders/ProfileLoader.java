package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.ProfileController;
import personal.zaytiri.taskerlyze.app.api.domain.Profile;
import personal.zaytiri.taskerlyze.ui.logic.entities.ProfileEntity;

import java.util.ArrayList;
import java.util.List;

public class ProfileLoader implements Loadable<ProfileEntity> {

    @Override
    public List<ProfileEntity> load() {
        List<ProfileEntity> profileToBeReturned = new ArrayList<>();

        List<Profile> profileResult = new ProfileController().get(null, null).getResult();
        for (Profile profile : profileResult) {
            profileToBeReturned.add(new ProfileEntity(profile));
        }

        return profileToBeReturned;
    }
}
