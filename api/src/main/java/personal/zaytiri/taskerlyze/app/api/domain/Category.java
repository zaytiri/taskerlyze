package personal.zaytiri.taskerlyze.app.api.domain;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.base.Entity;
import personal.zaytiri.taskerlyze.app.api.domain.base.IStorageOperations;
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.CategoryMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ICategoryRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category extends Entity<Category, ICategoryRepository, CategoryMapper> implements IStorageOperations<Category> {

    @Inject
    public Category(ICategoryRepository repository) {
        this.repository = repository;
        this.mapper = new CategoryMapper();
    }

    public Category() {
    }

    @Override
    public boolean createOrUpdate() {
        Response response;

        if (!exists()) {
            response = repository.create(this);
            this.id = response.getLastInsertedId();
        } else {
            response = repository.update(this);
        }
        return response.isSuccess();
    }

    @Override
    public boolean delete() {
        Response response = repository.delete(this);
        if (get() != null) {
            return false;
        }

        return response.isSuccess();
    }

    @Override
    public boolean exists() {
        Response response = repository.exists(this); // check done by name

        return !response.getResult().isEmpty();
    }

    @Override
    public Category get() {
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("id", new Pair<>("=", this.id));

        List<Category> results = get(filters, null);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    @Override
    public List<Category> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        Response response = repository.read(filters, orderByColumn);

        return mapper.toEntity(response.getResult(), false);
    }

    public Category setId(int id) {
        this.id = id;
        return this;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Category getInjectedComponent(AppComponent component) {
        return component.getCategory();
    }
}