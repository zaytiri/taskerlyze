package personal.zaytiri.taskerlyze.dependencyinjection;


import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
//    Type buildType();

}