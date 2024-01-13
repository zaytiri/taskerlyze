package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class PaneQuestion extends TitledPane {
    private final MenuOptions contextMenu;
    private final PaneQuestionDetails paneQuestionDetails = new PaneQuestionDetails();
    private final PropertyChangeSupport support;
    private int questionId;
    private String questionName;
    private boolean isAnswered;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label question;
    @FXML
    private BorderPane mainBorderPane;

    public PaneQuestion() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-question.fxml"));
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
        question.setText(this.questionName);
    }

    public void setContextMenu() {
        setContextMenu(getTabContextMenu());
    }

    public void setDetailsPane() {
        paneQuestionDetails.setQuestionId(getQuestionId());
        paneQuestionDetails.load();
        mainBorderPane.setCenter(paneQuestionDetails);
    }

    public void setIsQuestionAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
        checkBox.setSelected(this.isAnswered);
    }

    private void addAddQuestionOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new question", event -> PopupAction.showDialogForAddingQuestion((Accordion) this.getParent(), UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId(), evt -> reloadQuestions()));
    }

    private void addEditQuestionOptionForContextMenu() {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingQuestion(getQuestionId(), this, (Accordion) this.getParent(), evt -> reloadQuestions()));
    }

    private void addMoveQuestionOptionForContextMenu() {
        this.contextMenu.addMenuItem("Move", event -> {
            if (PopupAction.showDialogForMovingQuestion(getQuestionId())) {
                reloadQuestions();
                UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.WARNING, "Task was moved to another category. Please manual refresh new category.");
            }
        });
    }

    //
    private void addMoveToArchiveOptionForContextMenu() {
        this.contextMenu.addMenuItem("Move to Archive", event -> {
            QuestionEntity question = new QuestionEntity(getQuestionId()).get();
            question.setCategoryId(0);
            if (Boolean.TRUE.equals(question.update().getValue().getKey())) {
                reloadQuestions();
                UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.WARNING, "Task was moved to Archive. Please manual refresh Archive.");
            }
        });
    }

    private void addRemoveQuestionOptionForContextMenu() {
        this.contextMenu.addMenuItem("Remove (no confirmation)", event -> {
            QuestionEntity question = new QuestionEntity(getQuestionId());
            if (question.remove()) {
                reloadQuestions();
            }
        });
    }

    private ContextMenu getTabContextMenu() {
        addRemoveQuestionOptionForContextMenu();
        addMoveToArchiveOptionForContextMenu();
        addMoveQuestionOptionForContextMenu();
        addEditQuestionOptionForContextMenu();
        addAddQuestionOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    @FXML
    private void initialize() {
        setCheckBoxOnAction();
    }

    private void reloadQuestions() {
        support.firePropertyChange("toReload", false, true);
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            QuestionEntity question = new QuestionEntity(getQuestionId())
                    .setAnswered(checkBox.isSelected());
            question.setQuestionAsAnswered();
        });
    }

}
