package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import personal.zaytiri.taskerlyze.ui.logic.loaders.QuestionLoader;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;

import java.io.IOException;

public class ComponentMainView extends TabPane {
    @FXML
    private TabPane mainViewTabPane;
    @FXML
    private Tab tabTasks;
    @FXML
    private Tab tabQuestions;
    @FXML
    private Tab tabSettings;
    @FXML
    private Tab tabStatistics;

    public ComponentMainView() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-main-view.fxml"));

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
        setDefaultViews();
        setListenersForTabs();
        Platform.runLater(() -> mainViewTabPane.getSelectionModel().select(tabTasks));
    }

    private void setDefaultViews() {
        ComponentCategories categoriesForTasks = new ComponentCategories();
        categoriesForTasks.setView(new ComponentTasks());
        tabTasks.setContent(categoriesForTasks);

        ComponentCategories categoriesForQuestions = new ComponentCategories();
        categoriesForQuestions.setView(new ComponentQuestions());
        tabQuestions.setContent(categoriesForQuestions);
    }

    private void setListenersForTabs() {
        tabTasks.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                TaskLoader.getTaskLoader().load();
            }
        });

        tabQuestions.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                QuestionLoader.getQuestionLoader().load();
            }
        });
    }
}
