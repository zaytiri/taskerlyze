package personal.zaytiri.taskerlyze.ui.logic.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Profile;
import personal.zaytiri.taskerlyze.ui.logic.entities.ProfileEntity;

public class ProfileMapper implements IMapper<Profile, ProfileEntity> {
    @Override
    public Profile mapToApiObject(ProfileEntity profileEntity) {
        return new Profile().getInstance()
                .setId(profileEntity.getId())
                .setName(profileEntity.getName());
    }

    @Override
    public ProfileEntity mapToUiObject(Profile profile, ProfileEntity profileEntity) {
        return profileEntity
                .setId(profile.getId())
                .setName(profile.getName());
    }
}