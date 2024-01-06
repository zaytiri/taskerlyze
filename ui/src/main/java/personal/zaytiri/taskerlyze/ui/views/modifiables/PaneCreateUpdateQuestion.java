package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.QuestionLoader;

import java.io.IOException;

public class PaneCreateUpdateQuestion extends TitledPane {
    private final PaneCreateUpdateQuestionDetails paneQuestionDetails = new PaneCreateUpdateQuestionDetails();
    private QuestionEntity newOrExistingQuestion = new QuestionEntity();
    private int categoryId;
    private int questionId;
    @FXML
    private TextField questionName;
    @FXML
    private BorderPane mainBorderPane;

    public PaneCreateUpdateQuestion() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-question.fxml"));

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
        String errorMessageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            System.out.println(errorMessageFromApi);
            return;
        }

        Accordion parent = (Accordion) this.getParent();
        parent.getPanes().remove(this);
        QuestionLoader.getQuestionLoader().load();
    }

    @FXML
    private void initialize() {
        questionName.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                create();
                ev.consume();
            }
        });

        Platform.runLater(() -> {
            questionName.requestFocus();
            populate();

            questionName.selectAll();
            this.setExpanded(true);
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
}
