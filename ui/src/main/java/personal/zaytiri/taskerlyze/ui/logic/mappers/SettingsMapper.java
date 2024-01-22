package personal.zaytiri.taskerlyze.ui.logic.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Settings;
import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;

public class SettingsMapper implements IMapper<Settings, SettingsEntity> {
    @Override
    public Settings mapToApiObject(SettingsEntity settingsEntity) {
        return new Settings().getInstance()
                .setId(settingsEntity.getId())
                .setDarkMode(settingsEntity.isDarkMode())
                .setAlwaysOnTop(settingsEntity.isAlwaysOnTop())
                .setShowAchievedPopup(settingsEntity.isShowAchievedPopup())
                .setAutomaticReminders(settingsEntity.isAutomaticReminders())
                .setAutomaticTasks(settingsEntity.isAutomaticTasks())
                .setRemindQuestionsInDays(settingsEntity.getRemindQuestionsInDays())
                .setRemindTaskInDays(settingsEntity.getRemindTaskInDays())
                .setDefaultProfile(settingsEntity.getDefaultProfile());
    }

    @Override
    public SettingsEntity mapToUiObject(Settings settings, SettingsEntity settingsEntity) {
        return settingsEntity
                .setId(settings.getId())
                .setDarkMode(settings.isDarkMode())
                .setAlwaysOnTop(settings.isAlwaysOnTop())
                .setShowAchievedPopup(settings.isShowAchievedPopup())
                .setAutomaticReminders(settings.isAutomaticReminders())
                .setAutomaticTasks(settings.isAutomaticTasks())
                .setRemindQuestionsInDays(settings.getRemindQuestionsInDays())
                .setRemindTaskInDays(settings.getRemindTaskInDays())
                .setDefaultProfile(settings.getDefaultProfile());
    }

}