package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.ControllerFindable;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryController extends ControllerFindable<Category> {

    public OperationResult<List<Category>> getCategoriesByProfile(int profileId) {

        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("profile_id", new Pair<>("=", profileId));

        List<Category> results = new Category().getInstance().get(filters, null);

        MessageResult message = new MessageResult();
        if (results.isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!results.isEmpty(), message, results);
    }

    @Override
    protected Category getEntityInstance(int id) {
        return new Category().getInstance().setId(id);
    }

    @Override
    protected Category getEntityInstance() {
        return new Category().getInstance();
    }
}
