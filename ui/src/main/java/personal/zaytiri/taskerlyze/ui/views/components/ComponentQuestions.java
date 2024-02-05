package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.util.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalFilter;
import personal.zaytiri.taskerlyze.ui.logic.loaders.QuestionLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Cache;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Categorable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneQuestion;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class ComponentQuestions extends Categorable implements PropertyChangeListener {
    private final MenuOptions contextMenu;
    private final Map<Integer, Cache<PaneQuestion>> cache = new HashMap<>();
    private boolean reloadUiElements = true;
    private List<PaneQuestion> paneQuestions;
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
        var activeProfileId = UiGlobalFilter.getUiGlobalFilter().getActiveProfileId();

        var currentCache = cache.get(activeProfileId);

        if (currentCache == null) {
            cache.put(UiGlobalFilter.getUiGlobalFilter().getActiveProfileId(), new Cache<>());
            currentCache = cache.get(activeProfileId);
        }

        if (activeDay == null) {
            return;
        }

        var cacheKey = new Pair<>(getCategoryId(), activeDay);
        var cacheTasks = currentCache.findByKey(cacheKey);
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
        currentCache.replace(new Pair<>(getCategoryId(), activeDay), paneQuestions);
        setReload(false);
    }

    public void setReload(boolean toReload) {
        reloadUiElements = toReload;
    }

    @FXML
    public void initialize() {
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
