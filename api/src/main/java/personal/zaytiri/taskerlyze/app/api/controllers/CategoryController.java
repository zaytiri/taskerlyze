package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.ControllerFindable;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryController extends ControllerFindable<Category> {

    @Override
    public OperationResult<Category> delete(int id) {
        Category toBeDeleted = getEntityInstance(id);

        boolean isDeleted = toBeDeleted.delete();

        MessageResult message = new MessageResult();
        if (isDeleted) {

            Map<String, Pair<String, Object>> filters = new HashMap<>();
            filters.put("category_id", new Pair<>("=", id));

            List<Task> results = new Task().getInstance().get(filters, null);
            for (Task task : results) {
                task.setCategoryId(0);
                task.update();
            }

            message.setCode(CodeResult.DELETED);
        } else {
            message.setCode(CodeResult.NOT_DELETED);
        }

        return new OperationResult<>(isDeleted, message, null);
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
