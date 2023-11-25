package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.ArrayList;
import java.util.List;

public class SubTaskEntity extends Entity<SubTask, SubTaskEntity, SubTaskController> {
    private String name;
    private boolean isTaskDone;
    private int taskId;

    public SubTaskEntity() {
        api = new SubTaskController();
    }

    public SubTaskEntity(int id, String name, boolean isTaskDone, int taskId) {
        this();
        this.id = id;
        this.name = name;
        this.isTaskDone = isTaskDone;
        this.taskId = taskId;
    }

    public SubTaskEntity(SubTask subTask) {
        this(subTask.getId(), subTask.getName(), subTask.isDone(false), subTask.getTaskId());
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

    public SubTask mapToApiObject() {
        return new SubTask().getInstance()
                .setId(id)
                .setName(name)
                .setDone(isTaskDone)
                .setTaskId(taskId);
    }

    public SubTaskEntity mapToUiObject(SubTask entity) {
        return new SubTaskEntity(entity);
    }

    public SubTaskEntity setId(int id) {
        this.id = id;
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
