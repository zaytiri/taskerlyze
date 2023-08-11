package personal.zaytiri.taskerlyze.app.dependencyinjection;


import dagger.Component;
import javax.inject.Singleton;
import personal.zaytiri.taskerlyze.app.api.domain.Task;


@Singleton
@Component(modules = {RepositoriesModule.class})
public interface AppComponent {

    Task getTask();

}
