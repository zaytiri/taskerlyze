package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.QuestionLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Categorable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneQuestion;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

public class ComponentViews extends Categorable implements PropertyChangeListener {
    private final MenuOptions contextMenu;
    private ObservableList<QuestionEntity> questions;
    @FXML
    private Accordion mainQuestions;
    @FXML
    private TitledPane notFoundMessage;

    public ComponentViews() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-views.fxml"));
        this.contextMenu = new MenuOptions();

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void loadView() {
        QuestionLoader.getQuestionLoader().setActiveCategoryId(getCategoryId());
    }

    @FXML
    public void initialize() {
        QuestionLoader.getQuestionLoader().addPropertyChangeListener(this);
        this.notFoundMessage.setContextMenu(getTabContextMenu());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!(evt.getNewValue() instanceof List)) {
            return;
        }

        this.questions = FXCollections.observableList((List<QuestionEntity>) evt.getNewValue());
        if (this.questions.isEmpty() || this.questions.get(0).getCategoryId() == this.getCategoryId()) {
            setQuestions();
        }
    }

    private void addAddTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new question", event -> PopupAction.showDialogForAddingQuestion(mainQuestions, QuestionLoader.getQuestionLoader().getActiveCategoryId()));
    }

    private ContextMenu getTabContextMenu() {
        addAddTaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    public void setQuestions() {
        ObservableList<TitledPane> panes = mainQuestions.getPanes();

        panes.clear();

        if (questions.isEmpty()) {
            panes.add(0, notFoundMessage);
        }

        for (QuestionEntity q : questions) {
            PaneQuestion comp = new PaneQuestion();

            comp.setId(String.valueOf(q.getId()));
            comp.setQuestionId(q.getId());
            comp.setQuestionName(q.getQuestion());

//            comp.setContextMenu(event -> QuestionLoader.getQuestionLoader().load());

            panes.add(comp);
        }
    }
}
