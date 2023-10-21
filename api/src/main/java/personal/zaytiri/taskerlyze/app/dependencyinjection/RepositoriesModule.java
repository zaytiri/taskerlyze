package personal.zaytiri.taskerlyze.app.dependencyinjection;

import dagger.Binds;
import dagger.Module;
import personal.zaytiri.taskerlyze.app.persistence.repositories.CategoryRepository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.SubTaskRepository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.TaskRepository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ICategoryRepository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ISubTaskRepository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;

@Module
public abstract class RepositoriesModule {
    @Binds
    abstract ICategoryRepository bindCategory(CategoryRepository impl);

    @Binds
    abstract ISubTaskRepository bindSubTask(SubTaskRepository impl);

    @Binds
    abstract ITaskRepository bindTask(TaskRepository impl);
}
