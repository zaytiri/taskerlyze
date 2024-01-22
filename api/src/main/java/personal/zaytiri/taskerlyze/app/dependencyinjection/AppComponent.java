package personal.zaytiri.taskerlyze.app.dependencyinjection;


import dagger.Component;
import jakarta.inject.Singleton;
import personal.zaytiri.taskerlyze.app.api.domain.*;


@Singleton
@Component(modules = {RepositoriesModule.class})
public interface AppComponent {

    Category getCategory();

    Question getQuestion();

    Settings getSettings();

    SubTask getSubTask();

    Task getTask();
}
