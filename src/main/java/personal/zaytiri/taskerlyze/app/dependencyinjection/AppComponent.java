package personal.zaytiri.taskerlyze.app.dependencyinjection;


import dagger.Component;
import personal.zaytiri.taskerlyze.app.api.domain.Task;

import javax.inject.Singleton;

@Singleton
@Component(modules = {RepositoriesModule.class})
public interface AppComponent {

    Task getTask();

}
