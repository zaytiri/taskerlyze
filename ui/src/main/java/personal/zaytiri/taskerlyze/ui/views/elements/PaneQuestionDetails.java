package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.DateConversion;

import java.io.IOException;

public class PaneQuestionDetails extends BorderPane {
    private int questionId;
    @FXML
    private Label answer;
    @FXML
    private Label answeredAt;

    public PaneQuestionDetails() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-question-details.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void load() {
        QuestionEntity question = new QuestionEntity(questionId).get();

        answer.setText(question.getAnswer());
        answeredAt.setText(DateConversion.getFormattedDateForUi(question.getAnsweredAt()));
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }


}
