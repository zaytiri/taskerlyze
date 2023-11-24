package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Clipboard;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.io.IOException;

public class PaneTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    private final MenuOptions contextMenu;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label task;
    @FXML
    private Button swapButton;
    @FXML
    private Accordion subTasksAccordion;
    @FXML
    private TitledPane notFoundMessage;
    private SubTaskLoader subTaskLoader;

    public PaneTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-task.fxml"));
        this.contextMenu = new MenuOptions();

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
        task.setText(this.taskName.get());
    }

    public void setContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        setContextMenu(getTabContextMenu(ifSuccessful));
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }

    public void setSubTasks() {
        subTasksAccordion.getPanes().remove(0, subTasksAccordion.getPanes().size());

        subTaskLoader.setTaskId(getTaskId());
        ObservableList<SubTaskEntity> subTasks = FXCollections.observableList(subTaskLoader.load());

        if (subTasks.isEmpty()) {
            subTasksAccordion.getPanes().add(0, notFoundMessage);
        }

        for (SubTaskEntity st : subTasks) {
            PaneSubTask comp = new PaneSubTask();
            comp.setTaskName(st.getName());
            comp.setSubTaskId(st.getId());
            comp.setIsTaskDone(st.isTaskDone());
            comp.setTaskId(st.getTaskId());

            comp.setContextMenu(event -> setSubTasks());

            subTasksAccordion.getPanes().add(comp);
        }
    }

    private void addAddSubtaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Add new sub-task", event -> PopupAction.showDialogForAddingSubTask(getTaskId(), ifSuccessful));
    }

    private void addAddTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Add new task", event -> PopupAction.showDialogForAddingTask(ifSuccessful));
    }

    private void addCopyTextOptionForContextMenu() {
        this.contextMenu.addMenuItem("Copy text", event -> Clipboard.addTo(getTaskName()));
    }

    private void addCopyUrlOptionForContextMenu() {
        String taskUrl = new TaskEntity().get(getTaskId()).getUrl();
        this.contextMenu.addMenuItem("Copy URL", event -> Clipboard.addTo(taskUrl));
    }

    private void addEditTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingTask(getTaskId(), ifSuccessful));
    }

    private void addRemoveTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Remove (no confirmation)", event -> {
            TaskEntity task = new TaskEntity().setId(getTaskId());
            if (task.remove()) {
                ifSuccessful.handle(event);
            }
        });
    }

    private ContextMenu getTabContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        addAddTaskOptionForContextMenu(ifSuccessful);
        addEditTaskOptionForContextMenu(ifSuccessful);
        addRemoveTaskOptionForContextMenu(ifSuccessful);
        addAddSubtaskOptionForContextMenu(ifSuccessful);
        addCopyTextOptionForContextMenu();
        addCopyUrlOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    @FXML
    private void initialize() {
        subTaskLoader = new SubTaskLoader();
        setCheckBoxOnAction();
        populateSubTasksWhenTaskExpands();
    }

    private void populateSubTasksWhenTaskExpands() {
        this.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (Boolean.TRUE.equals(isNowExpanded)) {
                setSubTasks();
            }
        });
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            TaskEntity task = new TaskEntity()
                    .setId(getTaskId())
                    .setTaskDone(checkBox.isSelected());
            task.setDone();
        });
    }
}
