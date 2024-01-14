package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.KeyBindable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalMessage;

import java.io.IOException;

public class PaneCreateUpdateQuestion extends TitledPane {
    private final PaneCreateUpdateQuestionDetails paneQuestionDetails = new PaneCreateUpdateQuestionDetails();
    private final KeyBindable keyBinding;
    private QuestionEntity newOrExistingQuestion = new QuestionEntity();
    private int categoryId;
    private int questionId;
    @FXML
    private TextField questionName;
    @FXML
    private BorderPane mainBorderPane;
    private EventHandler<ActionEvent> ifSuccessful;

    public PaneCreateUpdateQuestion() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-question.fxml"));
        keyBinding = new KeyBindable();
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setIfSuccessful(EventHandler<ActionEvent> ifSuccessful) {
        this.ifSuccessful = ifSuccessful;
    }

    public void setQuestionId(int taskId) {
        this.questionId = taskId;
    }

    private void create() {
        newOrExistingQuestion = paneQuestionDetails.getQuestion();
        if (newOrExistingQuestion == null) {
            return;
        }

        newOrExistingQuestion
                .setQuestion(questionName.getText());

        if (categoryId != 0) {
            newOrExistingQuestion.setCategoryId(categoryId);
        }

        Pair<QuestionEntity, Pair<Boolean, String>> response = newOrExistingQuestion.createOrUpdate();
        boolean isSuccessfulFromApi = response.getValue().getKey();
        String messageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.ERROR, messageFromApi);
            return;
        }

        UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.NEUTRAL, messageFromApi);

        removePaneFromParent();
    }

    @FXML
    private void initialize() {
        keyBinding.addEnterKeyBinding(questionName, evt -> create());
        keyBinding.addEscapeKeyBinding(questionName, evt -> removePaneFromParent());

        Platform.runLater(() -> {
            populate();

            this.setExpanded(true);
            questionName.selectAll();
            questionName.requestFocus();
        });
    }

    private void populate() {
        if (this.questionId != 0) {
            newOrExistingQuestion = new QuestionEntity(this.questionId).get();
            questionName.setText(newOrExistingQuestion.getQuestion());
        }

        paneQuestionDetails.setQuestion(newOrExistingQuestion);
        paneQuestionDetails.load();
        mainBorderPane.setCenter(paneQuestionDetails);
    }

    private void removePaneFromParent() {
        Accordion parent = (Accordion) this.getParent();
        parent.getPanes().remove(this);
        this.ifSuccessful.handle(new ActionEvent());
    }
}
