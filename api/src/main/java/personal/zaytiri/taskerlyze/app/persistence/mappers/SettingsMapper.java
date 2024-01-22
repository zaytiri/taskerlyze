package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Settings;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.SettingsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingsMapper extends Mapper<Settings, SettingsModel> {
    public SettingsMapper() {
        super("settings__");
    }

    @Override
    public List<Settings> toEntity(List<Map<String, String>> rows, boolean mixedResult) {
        List<Settings> tasks = new ArrayList<>();

        for (Map<String, String> row : rows) {
            Settings task = new Settings().getInstance();

            task.setId(getRowIntValue(row, mixedResult, "id"));
            task.setDarkMode(getRowBooleanValue(row, mixedResult, "dark_mode"));
            task.setShowAchievedPopup(getRowBooleanValue(row, mixedResult, "show_achieved_popup"));
            task.setShowConfirmationPopup(getRowBooleanValue(row, mixedResult, "show_confirmation_popup"));
            task.setAlwaysOnTop(getRowBooleanValue(row, mixedResult, "always_on_top"));
            task.setAutomaticTasks(getRowBooleanValue(row, mixedResult, "automatic_tasks"));
            task.setAutomaticReminders(getRowBooleanValue(row, mixedResult, "automatic_reminders"));
            task.setRemindTaskInDays(getRowIntValue(row, mixedResult, "remind_tasks_in_days"));
            task.setRemindQuestionsInDays(getRowIntValue(row, mixedResult, "remind_questions_in_days"));
            task.setDefaultProfile(getRowIntValue(row, mixedResult, "default_profile"));

            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public Settings toEntity(SettingsModel model) {
        Settings entity = new Settings().getInstance();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setDarkMode(model.isDarkMode());
        entity.setShowAchievedPopup(model.isShowAchievedPopup());
        entity.setShowConfirmationPopup(model.isShowConfirmationPopup());
        entity.setAlwaysOnTop(model.isAlwaysOnTop());
        entity.setAutomaticTasks(model.isAutomaticTasks());
        entity.setAutomaticReminders(model.isAutomaticReminders());
        entity.setRemindTaskInDays(model.getRemindTaskInDays());
        entity.setRemindQuestionsInDays(model.getRemindQuestionsInDays());
        entity.setDefaultProfile(model.getDefaultProfile());

        return entity;
    }

    @Override
    public SettingsModel toModel(Settings entity) {
        SettingsModel model = new SettingsModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setDarkMode(entity.isDarkMode());
        model.setShowAchievedPopup(entity.isShowAchievedPopup());
        model.setShowConfirmationPopup(entity.isShowConfirmationPopup());
        model.setAlwaysOnTop(entity.isAlwaysOnTop());
        model.setAutomaticTasks(entity.isAutomaticTasks());
        model.setAutomaticReminders(entity.isAutomaticReminders());
        model.setRemindTaskInDays(entity.getRemindTaskInDays());
        model.setRemindQuestionsInDays(entity.getRemindQuestionsInDays());
        model.setDefaultProfile(entity.getDefaultProfile());

        return model;
    }
}
