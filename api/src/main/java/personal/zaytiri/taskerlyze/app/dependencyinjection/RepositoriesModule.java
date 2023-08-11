package personal.zaytiri.taskerlyze.app.dependencyinjection;

import dagger.Binds;
import dagger.Module;
import personal.zaytiri.taskerlyze.app.persistence.repositories.TaskRepository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;

@Module
public abstract class RepositoriesModule {
    @Binds
    abstract ITaskRepository bindTask(TaskRepository impl);

}
