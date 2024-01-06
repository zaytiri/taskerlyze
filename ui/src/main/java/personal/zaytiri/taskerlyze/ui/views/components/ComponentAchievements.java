package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import personal.zaytiri.taskerlyze.ui.logic.DateConversion;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComponentAchievements extends TitledPane implements PropertyChangeListener {
    private final List<String> achievements = new ArrayList<>();
    @FXML
    private TitledPane achievementsTitledPane;
    @FXML
    private Label achievementDate;
    @FXML
    private VBox achievementsContent;
    private LocalDate activeDay;

    public ComponentAchievements() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-achievements.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ((evt.getNewValue() instanceof LocalDate)) {
            this.activeDay = (LocalDate) evt.getNewValue();
        }

        if ((evt.getNewValue() instanceof List)) {
            List<TaskEntity> tasks = (List<TaskEntity>) evt.getNewValue();
            achievements.clear();
            for (TaskEntity t : tasks) {
                if (t.getAchieved().isEmpty()) {
                    continue;
                }
                achievements.add(t.getAchieved());
            }

        }
        showAchievements();
    }

    @FXML
    private void initialize() {
        TaskLoader.getTaskLoader().addPropertyChangeListener(this);

    }

    private boolean isNotPast() {
        return this.activeDay.isEqual(LocalDate.now()) || this.activeDay.isAfter(LocalDate.now());
    }

    private void showAchievements() {
        if (isNotPast()) {
            showAchievementsDisplay(false);
            return;
        }

        showAchievementsDisplay(true);

        achievementDate.setText(DateConversion.formatDateWithAbbreviatedMonth(this.activeDay));

        List<Node> children = achievementsContent.getChildren();

        children.clear();
        if (achievements.isEmpty()) {
            children.add(new Label("No achievements were set for this day."));
        }

        for (String achievement : achievements) {
            Label ach = new Label();
            ach.setText(achievement);
            children.add(ach);
        }

    }

    private void showAchievementsDisplay(boolean show) {
        achievementsTitledPane.setVisible(show);
        achievementsTitledPane.setManaged(show);
        achievementsTitledPane.setDisable(!show);
    }
}
