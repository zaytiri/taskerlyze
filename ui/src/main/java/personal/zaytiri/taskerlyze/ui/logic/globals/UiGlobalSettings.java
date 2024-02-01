package personal.zaytiri.taskerlyze.ui.logic.globals;

import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;

public class UiGlobalSettings {
    private static UiGlobalSettings INSTANCE;
    private boolean darkMode;
    private boolean showAchievedPopup;
    private boolean showConfirmationPopup;
    private boolean alwaysOnTop;
    private boolean automaticTasks;
    private boolean automaticReminders;
    private int remindTaskInDays;
    private int remindQuestionsInDays;
    private int defaultProfile;

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

    public static UiGlobalSettings getUiGlobalMessage() {
        if (INSTANCE == null) {
            INSTANCE = new UiGlobalSettings();
        }
        return INSTANCE;
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

    public void setSettings(SettingsEntity settings) {
        setDarkMode(settings.isDarkMode());
        setShowAchievedPopup(settings.isShowAchievedPopup());
        setShowConfirmationPopup(settings.isShowConfirmationPopup());
        setAlwaysOnTop(settings.isAlwaysOnTop());
        setAutomaticTasks(settings.isAutomaticTasks());
        setAutomaticReminders(settings.isAutomaticReminders());
        setRemindTaskInDays(settings.getRemindTaskInDays());
        setRemindQuestionsInDays(settings.getRemindQuestionsInDays());
        setDefaultProfile(settings.getDefaultProfile());
    }

}
