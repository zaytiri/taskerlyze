package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.DateConversion;

import java.io.IOException;

public class PaneCreateUpdateQuestionDetails extends BorderPane {
    private int questionId;
    @FXML
    private TextField answer;
    @FXML
    private Label answeredAt;
    private QuestionEntity question;

    public PaneCreateUpdateQuestionDetails() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-question-details.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public QuestionEntity getQuestion() {
        return question.setAnswer(answer.getText());
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public void load() {
        answer.setText(question.getAnswer());
        answeredAt.setText(DateConversion.getFormattedDateForUi(question.getAnsweredAt()));
    }
}
