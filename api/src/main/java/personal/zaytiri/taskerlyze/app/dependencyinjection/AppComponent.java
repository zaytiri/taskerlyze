package personal.zaytiri.taskerlyze.app.dependencyinjection;


import dagger.Component;
import jakarta.inject.Singleton;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.app.api.domain.Task;


@Singleton
@Component(modules = {RepositoriesModule.class})
public interface AppComponent {

    Category getCategory();

    Question getQuestion();

    SubTask getSubTask();

    Task getTask();
}
