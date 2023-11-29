package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

public class PaneTaskSubTasks extends Accordion implements PropertyChangeListener {
    private ObservableList<SubTaskEntity> subTasks;
    private int taskId;
    @FXML
    private TitledPane notFoundMessage;
    @FXML
    private Accordion subTasksAccordion;

    public PaneTaskSubTasks() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-task-subtasks.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.subTasks = FXCollections.observableList((List<SubTaskEntity>) evt.getNewValue());
        if (this.subTasks.isEmpty() || this.subTasks.get(0).getTaskId() == this.taskId) {
            setSubTasks();
        }
    }

    public void setSubTasks() {
        subTasksAccordion.getPanes().remove(0, subTasksAccordion.getPanes().size());

        if (subTasks.isEmpty()) {
            subTasksAccordion.getPanes().add(0, notFoundMessage);
        }

        for (SubTaskEntity st : subTasks) {
            PaneSubTask comp = new PaneSubTask();
            comp.setTaskName(st.getName());
            comp.setSubTaskId(st.getId());
            comp.setIsTaskDone(st.isTaskDone());
            comp.setTaskId(st.getTaskId());

            comp.setContextMenu(event -> {
                SubTaskLoader.getSubTaskLoader().setTaskId(st.getTaskId());
                SubTaskLoader.getSubTaskLoader().load();
            });

            subTasksAccordion.getPanes().add(comp);
        }
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @FXML
    private void initialize() {
        SubTaskLoader.getSubTaskLoader().addPropertyChangeListener(this);
    }
}
