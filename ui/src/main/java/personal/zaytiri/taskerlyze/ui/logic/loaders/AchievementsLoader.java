package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AchievementsLoader implements Loadable<String> {
    private final LocalDate date;

    public AchievementsLoader(LocalDate date) {
        this.date = date;
    }

    @Override
    public List<String> load() {
        List<String> achievementsToBeReturned = new ArrayList<>();

        OperationResult<List<String>> taskResult = new TaskController().getTaskAchievementsFromDay(date);
        for (String achievement : taskResult.getResult()) {
            achievementsToBeReturned.add("--> " + achievement);
        }

        return achievementsToBeReturned;
    }
}
