module api.main {
    requires java.sql;
    requires dagger;
    requires org.json;
    exports personal.zaytiri.taskerlyze.app.api.controllers;
}