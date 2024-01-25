package personal.zaytiri.taskerlyze.app.dependencyinjection;

import dagger.Binds;
import dagger.Module;
import personal.zaytiri.taskerlyze.app.persistence.repositories.*;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.*;

@Module
public abstract class RepositoriesModule {
    @Binds
    abstract ICategoryRepository bindCategory(CategoryRepository impl);

    @Binds
    abstract IProfileRepository bindProfile(ProfileRepository impl);

    @Binds
    abstract IQuestionRepository bindQuestion(QuestionRepository impl);

    @Binds
    abstract ISettingsRepository bindSettings(SettingsRepository impl);

    @Binds
    abstract ISubTaskRepository bindSubTask(SubTaskRepository impl);

    @Binds
    abstract ITaskRepository bindTask(TaskRepository impl);
}
