package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Profile;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.IMapper;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.ProfileModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileMapper extends Mapper<Profile, ProfileModel> implements IMapper<Profile, ProfileModel> {
    public ProfileMapper() {
        super("profiles__");
    }

    @Override
    public List<Profile> toEntity(List<Map<String, String>> rows, boolean mixedResult) {
        List<Profile> tasks = new ArrayList<>();

        for (Map<String, String> row : rows) {
            Profile task = new Profile().getInstance();

            task.setId(getRowIntValue(row, mixedResult, "id"));
            task.setName(getRowStringValue(row, mixedResult, "name"));

            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public Profile toEntity(ProfileModel model) {
        Profile entity = new Profile().getInstance();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setName(model.getName());

        return entity;
    }

    @Override
    public ProfileModel toModel(Profile entity) {
        ProfileModel model = new ProfileModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setName(entity.getName());

        return model;
    }
}
