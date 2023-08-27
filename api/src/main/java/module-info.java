module api.main {
    requires java.sql;
    requires dagger;
    requires org.json;
    exports personal.zaytiri.taskerlyze.app.api.controllers;
    exports personal.zaytiri.taskerlyze.app.api.domain;
    exports personal.zaytiri.taskerlyze.app.api.controllers.result;
    exports personal.zaytiri.taskerlyze.libraries.pairs;
}