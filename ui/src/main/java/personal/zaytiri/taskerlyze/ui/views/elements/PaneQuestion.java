package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.QuestionLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.io.IOException;

public class PaneQuestion extends TitledPane {
    private final MenuOptions contextMenu;
    private final PaneQuestionDetails paneQuestionDetails = new PaneQuestionDetails();
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

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
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
//
//    public void setContextMenu(EventHandler<ActionEvent> ifSuccessful) {
//        setContextMenu(getTabContextMenu(ifSuccessful));
//    }
//
//    private void addAddTaskOptionForContextMenu() {
//        this.contextMenu.addMenuItem("Add new task", event -> PopupAction.showDialogForAddingTask((Accordion) this.getParent(), TaskLoader.getTaskLoader().getActiveCategoryId()));
//    }
//
//    private void addEditTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
//        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingTask(getTaskId(), this, (Accordion) this.getParent()));
//    }
//
//    private void addMoveTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
//        this.contextMenu.addMenuItem("Move", event -> PopupAction.showDialogForMovingTask(getTaskId(), ifSuccessful));
//    }
//
//    private void addMoveToArchiveOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
//        this.contextMenu.addMenuItem("Move to Archive", event -> {
//            TaskEntity task = new TaskEntity(getTaskId()).get();
//            task.setCategoryId(0);
//            if (task.update().getValue().getKey()) {
//                ifSuccessful.handle(event);
//            }
//        });
//    }
//
//
//    private void addRemoveTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
//        this.contextMenu.addMenuItem("Remove (no confirmation)", event -> {
//            TaskEntity task = new TaskEntity(getTaskId());
//            if (task.remove()) {
//                ifSuccessful.handle(event);
//            }
//        });
//    }
//
//    private ContextMenu getTabContextMenu(EventHandler<ActionEvent> ifSuccessful) {
//        addAddTaskOptionForContextMenu();
//        addEditTaskOptionForContextMenu(ifSuccessful);
//        addRemoveTaskOptionForContextMenu(ifSuccessful);
//        addMoveTaskOptionForContextMenu(ifSuccessful);
//        addMoveToArchiveOptionForContextMenu(ifSuccessful);
//
//        return contextMenu.buildContextMenu();
//    }
    public void setDetailsPane() {
        paneQuestionDetails.setQuestionId(getQuestionId());
        paneQuestionDetails.load();
        mainBorderPane.setCenter(paneQuestionDetails);
    }

    public void setIsQuestionAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
        checkBox.setSelected(this.isAnswered);
    }

    @FXML
    private void initialize() {
        setCheckBoxOnAction();
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            QuestionEntity question = new QuestionEntity(getQuestionId())
                    .setAnswered(checkBox.isSelected());
            question.setQuestionAsAnswered();
        });
    }

}
