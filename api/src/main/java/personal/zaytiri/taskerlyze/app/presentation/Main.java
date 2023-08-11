package personal.zaytiri.taskerlyze.app.presentation;

import personal.zaytiri.taskerlyze.app.persistence.DbConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        DbConnection database = DbConnection.getInstance();

        database.open();
        database.close();

    }
}