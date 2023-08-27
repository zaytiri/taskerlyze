package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.domain.Category;

public class CategoryController extends Controller<Category> {

    @Override
    protected Category getEntityInstance(int id) {
        return new Category().getInstance().setId(id);
    }

    @Override
    protected Category getEntityInstance() {
        return new Category().getInstance();
    }
}
