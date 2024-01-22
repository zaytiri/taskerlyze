package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.SettingsController;
import personal.zaytiri.taskerlyze.app.api.domain.Settings;
import personal.zaytiri.taskerlyze.ui.logic.mappers.SettingsMapper;

public class SettingsEntity extends Entity<Settings, SettingsEntity, SettingsController> {
    private boolean darkMode;
    private boolean showAchievedPopup;
    private boolean alwaysOnTop;
    private boolean automaticTasks;
    private boolean automaticReminders;
    private int remindTaskInDays;
    private int remindQuestionsInDays;
    private int defaultProfile;
    private SettingsEntity INSTANCE;

    private SettingsEntity() {
        this(0);
    }

    private SettingsEntity(Settings settings) {
        this();
        mapper.mapToUiObject(settings, this);
    }

    private SettingsEntity(int id) {
        super(id);
        api = new SettingsController();
        mapper = new SettingsMapper();
    }

    public int getDefaultProfile() {
        return defaultProfile;
    }

    public SettingsEntity setDefaultProfile(int defaultProfile) {
        this.defaultProfile = defaultProfile;
        return this;
    }

    public int getRemindQuestionsInDays() {
        return remindQuestionsInDays;
    }

    public SettingsEntity setRemindQuestionsInDays(int remindQuestionsInDays) {
        this.remindQuestionsInDays = remindQuestionsInDays;
        return this;
    }

    public int getRemindTaskInDays() {
        return remindTaskInDays;
    }

    public SettingsEntity setRemindTaskInDays(int remindTaskInDays) {
        this.remindTaskInDays = remindTaskInDays;
        return this;
    }

    public SettingsEntity getSettingsInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsEntity();
        }
        return INSTANCE;
    }

    public boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }

    public SettingsEntity setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
        return this;
    }

    public boolean isAutomaticReminders() {
        return automaticReminders;
    }

    public SettingsEntity setAutomaticReminders(boolean automaticReminders) {
        this.automaticReminders = automaticReminders;
        return this;
    }

    public boolean isAutomaticTasks() {
        return automaticTasks;
    }

    public SettingsEntity setAutomaticTasks(boolean automaticTasks) {
        this.automaticTasks = automaticTasks;
        return this;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public SettingsEntity setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        return this;
    }

    public boolean isShowAchievedPopup() {
        return showAchievedPopup;
    }

    public SettingsEntity setShowAchievedPopup(boolean showAchievedPopup) {
        this.showAchievedPopup = showAchievedPopup;
        return this;
    }

    public SettingsEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected SettingsEntity getObject() {
        return this;
    }
}
