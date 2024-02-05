package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.makeitexplicitlyqueryable.pairs.Pair;
import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SettingsModel extends Model {

    private boolean darkMode;
    private boolean showAchievedPopup;
    private boolean showConfirmationPopup;
    private boolean alwaysOnTop;
    private boolean automaticTasks;
    private boolean automaticReminders;
    private int remindTaskInDays;
    private int remindQuestionsInDays;
    private int defaultProfile;
    private LocalDate updatedAt;
    private LocalDate createdAt;

    public SettingsModel() {
        super("settings");
    }

    @Override
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public List<Pair<String, Object>> getValues() {
        List<Pair<String, Object>> values = new ArrayList<>();

//        values.put("id", id); // its commented out because it does not need to be inserted, it will auto increment
        values.add(new Pair<>("dark_mode", darkMode));
        values.add(new Pair<>("show_achieved_popup", showAchievedPopup));
        values.add(new Pair<>("show_confirmation_popup", showConfirmationPopup));
        values.add(new Pair<>("always_on_top", alwaysOnTop));
        values.add(new Pair<>("automatic_tasks", automaticTasks));
        values.add(new Pair<>("automatic_reminders", automaticReminders));
        values.add(new Pair<>("remind_tasks_in_days", remindTaskInDays));
        values.add(new Pair<>("remind_questions_in_days", remindQuestionsInDays));
        values.add(new Pair<>("default_profile", defaultProfile));
        values.add(new Pair<>("updated_at", updatedAt));
        values.add(new Pair<>("created_at", createdAt));

        return values;
    }

    public int getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(int defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public int getRemindQuestionsInDays() {
        return remindQuestionsInDays;
    }

    public void setRemindQuestionsInDays(int remindQuestionsInDays) {
        this.remindQuestionsInDays = remindQuestionsInDays;
    }

    public int getRemindTaskInDays() {
        return remindTaskInDays;
    }

    public void setRemindTaskInDays(int remindTaskInDays) {
        this.remindTaskInDays = remindTaskInDays;
    }

    public boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }

    public boolean isAutomaticReminders() {
        return automaticReminders;
    }

    public void setAutomaticReminders(boolean automaticReminders) {
        this.automaticReminders = automaticReminders;
    }

    public boolean isAutomaticTasks() {
        return automaticTasks;
    }

    public void setAutomaticTasks(boolean automaticTasks) {
        this.automaticTasks = automaticTasks;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public boolean isShowAchievedPopup() {
        return showAchievedPopup;
    }

    public void setShowAchievedPopup(boolean showAchievedPopup) {
        this.showAchievedPopup = showAchievedPopup;
    }

    public boolean isShowConfirmationPopup() {
        return showConfirmationPopup;
    }

    public void setShowConfirmationPopup(boolean showConfirmationPopup) {
        this.showConfirmationPopup = showConfirmationPopup;
    }
}
