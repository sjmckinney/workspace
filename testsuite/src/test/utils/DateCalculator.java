package utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public static class DateCalculator
{
    public string CalculateDateWithOffset(TimeFrame offset)
    {
        String dateWithOffset;
        
        try {
            LocalDate date = LocalDate.now().plusDays(2);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            dateWithOffset = dateFormat.format(date);
        } catch (Exception e) {
            System.out.println("{}" + e.message);
        }
        return dateWithOffset;
    }
}