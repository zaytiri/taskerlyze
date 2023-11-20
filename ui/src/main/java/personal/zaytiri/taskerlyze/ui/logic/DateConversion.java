package personal.zaytiri.taskerlyze.ui.logic;

import java.time.LocalDate;

public class DateConversion {

    public static LocalDate dateToLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public static String getNameOfMonthCapitalized(LocalDate date) {
        String monthName = date.getMonth().name().toLowerCase();
        return monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
    }

}
