package personal.zaytiri.taskerlyze.ui.logic.loaders;

import com.google.common.base.Stopwatch;
import personal.zaytiri.taskerlyze.app.api.controllers.QuestionController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionLoader {
    private static QuestionLoader INSTANCE;
    private final PropertyChangeSupport support;
    private final List<QuestionEntity> loadedQuestions = new ArrayList<>();
    private LocalDate activeDay = null;
    private int activeCategoryId = 0;

    private QuestionLoader() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public int getActiveCategoryId() {
        return activeCategoryId;
    }

    public void setActiveCategoryId(int currentActiveCategoryId) {
        activeCategoryId = currentActiveCategoryId;
        load();
    }

    public LocalDate getActiveDay() {
        return activeDay;
    }

    public void setActiveDay(LocalDate currentActiveDay) {
        support.firePropertyChange("activeDay", activeDay, currentActiveDay);
        activeDay = currentActiveDay;
        load();
    }

    public static QuestionLoader getQuestionLoader() {
        if (INSTANCE == null) {
            INSTANCE = new QuestionLoader();
        }
        return INSTANCE;
    }

    public void load() {
        if (activeDay == null) {
            return;
        }

        Stopwatch loadSW = Stopwatch.createStarted();

        OperationResult<List<Question>> questionResult = new QuestionController().getQuestionsByCategoryAndAnsweredAtDate(activeCategoryId, activeDay);

        List<QuestionEntity> questionToBeReturned = new ArrayList<>();
        for (Question question : questionResult.getResult()) {
            questionToBeReturned.add(new QuestionEntity(question));
        }
        support.firePropertyChange("loadedQuestions", loadedQuestions, questionToBeReturned);

        loadedQuestions.clear();
        loadedQuestions.addAll(questionToBeReturned);

        loadSW.stop();
        System.out.println("load took " + loadSW.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    public void refresh() {
        load();
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
