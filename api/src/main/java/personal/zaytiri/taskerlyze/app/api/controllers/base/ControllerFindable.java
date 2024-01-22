package personal.zaytiri.taskerlyze.app.api.controllers.base;

import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.base.IFindable;

import java.util.List;

public abstract class ControllerFindable<T extends IFindable<T>> extends Controller<T> implements IControllerFindable<T> {

    @Override
    public OperationResult<List<T>> findNameBySubString(String subString) {
        T entity = getEntityInstance();

        List<T> tasksBySubString = entity.findNameBySubString(subString);

        MessageResult message = new MessageResult();
        if (tasksBySubString.isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!tasksBySubString.isEmpty(), message, tasksBySubString);
    }

    protected abstract T getEntityInstance(int id);

    protected abstract T getEntityInstance();
}
