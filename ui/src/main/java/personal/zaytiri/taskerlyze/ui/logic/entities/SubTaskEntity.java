package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.mappers.SubTaskMapper;

import java.util.ArrayList;
import java.util.List;

public class SubTaskEntity extends Entity<SubTask, SubTaskEntity, SubTaskController> {
    private String name;
    private boolean isTaskDone;
    private int taskId;

    public SubTaskEntity(int id) {
        super(id);
        api = new SubTaskController();
        mapper = new SubTaskMapper();
    }

    public SubTaskEntity() {
        this(0);
    }

    public SubTaskEntity(SubTask subTask) {
        this();
        mapper.mapToUiObject(subTask, this);
    }

    @Override
    public List<Pair<Integer, String>> findBySubString(String subString) {
        OperationResult<List<SubTask>> result = api.findNameBySubString(subString);
        List<Pair<Integer, String>> subtaskToBeReturned = new ArrayList<>();
        for (SubTask subtask : result.getResult()) {
            subtaskToBeReturned.add(new Pair<>(subtask.getId(), subtask.getName()));
        }
        return subtaskToBeReturned;
    }

    public SubTaskEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected SubTaskEntity getObject() {
        return this;
    }

    public String getName() {
        return name;
    }

    public SubTaskEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getTaskId() {
        return taskId;
    }

    public SubTaskEntity setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public SubTaskEntity setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
        return this;
    }

    public void setDone() {
        api.setDone(id, isTaskDone);
    }
}
