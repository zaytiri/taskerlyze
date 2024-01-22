package personal.zaytiri.taskerlyze.app.persistence.repositories;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.Settings;
import personal.zaytiri.taskerlyze.app.persistence.mappers.SettingsMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.SettingsModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ISettingsRepository;

public class SettingsRepository extends Repository<Settings, SettingsModel, SettingsMapper> implements ISettingsRepository {

    @Inject
    public SettingsRepository() {
        super(new SettingsModel(), new SettingsMapper());
    }

}
