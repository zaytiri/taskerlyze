package personal.zaytiri.taskerlyze.app.api.domain;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.base.Entity;
import personal.zaytiri.taskerlyze.app.api.domain.base.IStorageOperations;
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.SettingsMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ISettingsRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings extends Entity<Settings, ISettingsRepository, SettingsMapper> implements IStorageOperations<Settings> {

    private boolean darkMode;
    private boolean showAchievedPopup;
    private boolean alwaysOnTop;
    private boolean automaticTasks;
    private boolean automaticReminders;
    private int remindTaskInDays;
    private int remindQuestionsInDays;
    private int defaultProfile;

    @Inject
    public Settings(ISettingsRepository repository) {
        this.repository = repository;
        this.mapper = new SettingsMapper();

    }

    public Settings() {
    }

    public boolean create() {
        Response response = repository.create(this);
        this.id = response.getLastInsertedId();

        return response.isSuccess();
    }

    public boolean delete() {
        Response response = repository.delete(this);
        if (get() != null) {
            return false;
        }

        return response.isSuccess();
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public List<Settings> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        return null;
    }

    public Settings get() {
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("id", new Pair<>("=", this.id));

        List<Settings> results = get(filters, null);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    public boolean update() {
        //todo: test what happens if i try to update but theres no entry to update because its not created yet.
        return repository.update(this).isSuccess();
    }

    public int getDefaultProfile() {
        return defaultProfile;
    }

    public Settings setDefaultProfile(int defaultProfile) {
        this.defaultProfile = defaultProfile;
        return this;
    }

    public int getRemindQuestionsInDays() {
        return remindQuestionsInDays;
    }

    public Settings setRemindQuestionsInDays(int remindQuestionsInDays) {
        this.remindQuestionsInDays = remindQuestionsInDays;
        return this;
    }

    public int getRemindTaskInDays() {
        return remindTaskInDays;
    }

    public Settings setRemindTaskInDays(int remindTaskInDays) {
        this.remindTaskInDays = remindTaskInDays;
        return this;
    }

    public boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }

    public Settings setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
        return this;
    }

    public boolean isAutomaticReminders() {
        return automaticReminders;
    }

    public Settings setAutomaticReminders(boolean automaticReminders) {
        this.automaticReminders = automaticReminders;
        return this;
    }

    public boolean isAutomaticTasks() {
        return automaticTasks;
    }

    public Settings setAutomaticTasks(boolean automaticTasks) {
        this.automaticTasks = automaticTasks;
        return this;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public Settings setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        return this;
    }

    public boolean isShowAchievedPopup() {
        return showAchievedPopup;
    }

    public Settings setShowAchievedPopup(boolean showAchievedPopup) {
        this.showAchievedPopup = showAchievedPopup;
        return this;
    }

    public Settings setId(int id) {
        this.id = id;
        return this;
    }

    public Settings setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Settings getInjectedComponent(AppComponent component) {
        return component.getSettings();
    }

}
