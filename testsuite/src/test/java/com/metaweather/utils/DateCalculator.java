package com.metaweather.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCalculator
{
    public static String CalculateDateWithOffset(TimeFrame offset)
    {
        String dateWithOffset = "";
        
        try {
            LocalDate date = LocalDate.now().plusDays(offset.value);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            dateWithOffset = dateFormat.format(date);
        } catch (Exception e) {
            System.out.println("{}" + e.getMessage());
        }
        return dateWithOffset;
    }
}