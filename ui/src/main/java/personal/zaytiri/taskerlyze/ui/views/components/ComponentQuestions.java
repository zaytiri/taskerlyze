package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.QuestionLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.*;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneQuestion;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComponentQuestions extends Categorable implements PropertyChangeListener {
    private final MenuOptions contextMenu;
    private boolean reloadUiElements = true;
    private List<PaneQuestion> paneQuestions;
    private Cache<PaneQuestion> cache;
    private List<QuestionEntity> questions;
    @FXML
    private Accordion mainQuestions;
    @FXML
    private PaneQuestion notFoundMessage;

    public ComponentQuestions() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-questions.fxml"));
        this.contextMenu = new MenuOptions();

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @Override
    public Categorable getNewInstance() {
        return new ComponentQuestions();
    }

    public void loadView() {
        var activeCategoryId = UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId();
        var activeDay = UiGlobalFilter.getUiGlobalFilter().getActiveDay();

        if (activeDay == null) {
            return;
        }

        var cacheKey = new Pair<>(getCategoryId(), activeDay);
        var cacheTasks = cache.findByKey(cacheKey);
        if (!reloadUiElements && cacheTasks != null) {
            mainQuestions.getPanes().clear();
            mainQuestions.getPanes().addAll(cacheTasks.getValue());
            return;
        }

        var loader = new QuestionLoader();
        loader.setCategoryId(activeCategoryId);
        loader.setDate(activeDay);
        var questions = loader.load();

        if (questions == null) {
            return;
        }

        this.questions = questions;
        setQuestions();
        mainQuestions.getPanes().clear();
        mainQuestions.getPanes().addAll(paneQuestions);
        cache.replace(new Pair<>(getCategoryId(), activeDay), paneQuestions);
        setReload(false);
    }

    public void setReload(boolean toReload) {
        reloadUiElements = toReload;
    }

    @FXML
    public void initialize() {
        cache = new Cache<>();
        UiGlobalFilter.getUiGlobalFilter().addPropertyChangeListener(this);
        this.notFoundMessage.setContextMenu(getTabContextMenu());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!Objects.equals(evt.getPropertyName(), "toReload")) {
            return;
        }

        var activeCategoryId = UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId();
        if (getCategoryId() != activeCategoryId) {
            return;
        }

        setReload((boolean) evt.getNewValue());
        loadView();
    }

    public void setQuestions() {
        paneQuestions = new ArrayList<>();


        if (questions.isEmpty()) {
            paneQuestions.add(0, notFoundMessage);
        }

        for (QuestionEntity q : questions) {
            PaneQuestion comp = new PaneQuestion();

            comp.addPropertyChangeListener(this);
            comp.setId(String.valueOf(q.getId()));
            comp.setQuestionId(q.getId());
            comp.setQuestionName(q.getQuestion());
            comp.setIsQuestionAnswered(q.isAnswered());

            comp.setDetailsPane();
            comp.setContextMenu();

            paneQuestions.add(comp);
        }
    }

    private void addAddTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new question", event -> PopupAction.showDialogForAddingQuestion(mainQuestions, UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId(), evt -> {
            setReload(true);
            this.loadView();
        }));
    }

    private ContextMenu getTabContextMenu() {
        addAddTaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }
}
