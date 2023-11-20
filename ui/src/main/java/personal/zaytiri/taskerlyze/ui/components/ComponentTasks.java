package personal.zaytiri.taskerlyze.ui.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.ui.logic.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ComponentTasks extends Accordion implements PropertyChangeListener {
    private ObservableList<TaskEntity> tasks;
    @FXML
    private Accordion mainTasks;
    @FXML
    private TitledPane notFoundMessage;

    public ComponentTasks() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-tasks.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @FXML
    public void initialize() {
        TaskLoader.getTaskLoader().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.tasks = FXCollections.observableList((List<TaskEntity>) evt.getNewValue());
        setTasks();
    }

    private void setTasks() {
        TitledPane expandedPane = mainTasks.getExpandedPane();
        mainTasks.getPanes().remove(0, mainTasks.getPanes().size());

        if (tasks.isEmpty()) {
            mainTasks.getPanes().add(0, notFoundMessage);
        }

        for (TaskEntity t : tasks) {
            PaneTask comp = new PaneTask();

            comp.setId(String.valueOf(t.getId()));
            comp.setTaskId(t.getId());
            comp.setTaskName(t.getName());
            comp.setIsTaskDone(t.isTaskDone());

            comp.setSubTasks();

            if (expandedPane != null && Objects.equals(expandedPane.getId(), comp.getId())) {
                mainTasks.setExpandedPane(comp);
            }

            mainTasks.getPanes().add(comp);
        }
    }
}
