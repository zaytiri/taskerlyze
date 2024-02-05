package personal.zaytiri.taskerlyze.app.api.domain;

import jakarta.inject.Inject;
import personal.zaytiri.makeitexplicitlyqueryable.pairs.Pair;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.base.Entity;
import personal.zaytiri.taskerlyze.app.api.domain.base.IStorageOperations;
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.ProfileMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.IProfileRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile extends Entity<Profile, IProfileRepository, ProfileMapper> implements IStorageOperations<Profile> {

    @Inject
    public Profile(IProfileRepository repository) {
        this.repository = repository;
        this.mapper = new ProfileMapper();
    }

    public Profile() {
    }

    public boolean create() {
        if (exists()) {
            return false;
        }

        Response response = repository.create(this);
        this.id = response.getLastInsertedId();

        return response.isSuccess();
    }

    public boolean delete() {
        Response response = repository.delete(this);
        if (get() != null) {
            return false;
        }

        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("profile_id", new Pair<>("=", id));

        List<Category> results = new Category().getInstance().get(filters, null);
        for (Category category : results) {
            category.delete();
        }

        return response.isSuccess();
    }

    public boolean exists() {
        Response response = repository.exists(this); // check done by name

        return !response.getResult().isEmpty();
    }

    public List<Profile> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        Response response = repository.read(filters, orderByColumn);

        return mapper.toEntity(response.getResult(), false);
    }

    public Profile get() {
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("id", new Pair<>("=", this.id));

        List<Profile> results = get(filters, null);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    public boolean update() {
        //todo: test what happens if i try to update but theres no entry to update because its not created yet.
        return repository.update(this).isSuccess();
    }

    public Profile setId(int id) {
        this.id = id;
        return this;
    }

    public Profile setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Profile getInjectedComponent(AppComponent component) {
        return component.getProfile();
    }

}
