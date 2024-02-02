package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalFilter;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalSettings;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Clipboard;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;
import personal.zaytiri.taskerlyze.ui.views.popups.DialogAddAchieved;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;

public class PaneTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    private final MenuOptions contextMenu;
    private final PaneTaskSubTasks paneTaskSubTasks = new PaneTaskSubTasks();
    private final PaneTaskDetails paneTaskDetails = new PaneTaskDetails();
    private final PropertyChangeSupport support;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label task;
    @FXML
    private Button swapButton;
    @FXML
    private BorderPane mainBorderPane;

    public PaneTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-task.fxml"));
        this.contextMenu = new MenuOptions();

        support = new PropertyChangeSupport(this);

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
        setSubTasksPane();
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
        task.setText(this.taskName.get());
    }

    public void setContextMenu() {
        setContextMenu(getTabContextMenu());
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }


    private void addAddTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new task", event -> PopupAction.showDialogForAddingTask((Accordion) this.getParent(), UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId(), evt -> reloadTasks()));
    }

    private void addCopyTextOptionForContextMenu() {
        this.contextMenu.addMenuItem("Copy text", event -> Clipboard.addTo(getTaskName()));
    }

    private void addCopyUrlOptionForContextMenu() {
        String taskUrl = new TaskEntity(getTaskId()).get().getUrl();
        this.contextMenu.addMenuItem("Copy URL", event -> Clipboard.addTo(taskUrl));
    }

    private void addEditTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingTask(getTaskId(), this, (Accordion) this.getParent(), evt -> reloadTasks()));
    }

    private void addMoveTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Move", event -> PopupAction.showDialogForMovingTask(getTaskId(), evt -> {
            reloadTasks();
            UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.WARNING, "Task was moved to another category. Please manual refresh new category.");
        }));
    }

    private void addMoveToArchiveOptionForContextMenu() {
        this.contextMenu.addMenuItem("Move to Archive", event -> {
            TaskEntity task = new TaskEntity(getTaskId()).get();
            task.setCategoryId(0);
            if (Boolean.TRUE.equals(task.update().getValue().getKey())) {
                reloadTasks();
                UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.WARNING, "Task was moved to Archive. Please manual refresh Archive.");
            }
        });
    }

    private void addOpenURLTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Open URL in browser", event -> {
            String taskUrl = new TaskEntity(getTaskId()).get().getUrl();
            try {
                Desktop.getDesktop().browse(new URL(taskUrl).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void addRemoveTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Remove", event -> {
            PopupAction.showConfirmationDialog(
                    "You are about to remove the following task: '" + getTaskName() + "'.",
                    evt -> {
                        TaskEntity task = new TaskEntity(getTaskId());
                        if (task.remove()) {
                            reloadTasks();
                        }
                    },
                    true
            );
        });
    }

    private ContextMenu getTabContextMenu() {
        addAddTaskOptionForContextMenu();
        addEditTaskOptionForContextMenu();
        addMoveTaskOptionForContextMenu();
        addMoveToArchiveOptionForContextMenu();
        addCopyTextOptionForContextMenu();
        addCopyUrlOptionForContextMenu();
        addOpenURLTaskOptionForContextMenu();
        addRemoveTaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    @FXML
    private void initialize() {
        setSwapButtonOnAction();
        setCheckBoxOnAction();
        populateSubTasksWhenTaskExpands();
    }

    private void loadSubTasks() {
        SubTaskLoader.getSubTaskLoader().setTaskId(getTaskId());
        SubTaskLoader.getSubTaskLoader().load();
    }

    private void populateSubTasksWhenTaskExpands() {
        this.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (Boolean.TRUE.equals(isNowExpanded)) {
                loadSubTasks();
            }
        });
    }

    private void reloadTasks() {
        support.firePropertyChange("toReload", false, true);
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            TaskEntity task = new TaskEntity(getTaskId())
                    .setTaskDone(checkBox.isSelected());
            task.setDone();

            if (!task.isTaskDone()) {
                return;
            }

            if (UiGlobalSettings.getUiGlobalMessage().isShowAchievedPopup()) {
                DialogAddAchieved addAchievedPopUp = new DialogAddAchieved();
                addAchievedPopUp.setTaskId(getTaskId());
                addAchievedPopUp.showDialog();
            }
        });
    }

    private void setDetailsPane() {
        paneTaskDetails.setTaskId(getTaskId());
        paneTaskDetails.load();
        mainBorderPane.setCenter(paneTaskDetails);
    }

    private void setSubTasksPane() {
        paneTaskSubTasks.setTaskId(getTaskId());
        mainBorderPane.setCenter(paneTaskSubTasks);
    }

    private void setSwapButtonOnAction() {
        swapButton.setOnAction(event -> {
            Node currentCenterNode = mainBorderPane.getCenter();
            mainBorderPane.getChildren().remove(currentCenterNode);

            if (currentCenterNode instanceof PaneTaskSubTasks) {
                setDetailsPane();
            } else if (currentCenterNode instanceof PaneTaskDetails) {
                setSubTasksPane();
            }
        });
    }
}
