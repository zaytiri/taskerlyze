package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.ProfileController;
import personal.zaytiri.taskerlyze.app.api.domain.Profile;
import personal.zaytiri.taskerlyze.ui.logic.mappers.ProfileMapper;

public class ProfileEntity extends Entity<Profile, ProfileEntity, ProfileController> {

    private String name;

    public ProfileEntity() {
        this(0);
    }

    public ProfileEntity(Profile profile) {
        this();
        mapper.mapToUiObject(profile, this);
    }

    public ProfileEntity(int id) {
        super(id);
        api = new ProfileController();
        mapper = new ProfileMapper();
    }

    public String getName() {
        return name;
    }

    public ProfileEntity setName(String name) {
        this.name = name;
        return this;
    }

    public ProfileEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected ProfileEntity getObject() {
        return this;
    }
}
