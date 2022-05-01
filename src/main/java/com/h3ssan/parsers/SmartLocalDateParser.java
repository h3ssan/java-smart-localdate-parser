package com.h3ssan.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SmartLocalDateParser {
    private final String date;

    public SmartLocalDateParser(String date) {
        this.date = preProcessing(date);
    }

    /**
     * A method which responsible for filtering the date parameter and make sure
     * that the passed String date is clean
     *
     * @param  date  a String which represents a date
     * @return       a Cleaned String date which is the final modification of the date
     * @see          String
     */
    private String preProcessing(String date) {
        if (Pattern.compile("[٠١٢٣٤٥٦٧٨٩]").matcher(date).find())
            return replaceArabicNumbers(date);

        return date;
    }

    /**
     * Method that replaces all numbers from Arabic to English
     *
     * @param  date  A String which represents a date
     * @return       String date which all numbers in English
     * @see          String
     */
    private String replaceArabicNumbers(String date) {
        List<String> arabicNumbers = new ArrayList<>(Arrays.asList("٠", "١", "٢", "٣" ,"٤", "٥", "٦", "٧", "٨", "٩"));
        List<String> englishNumbers = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        String temp = date;

        for (int i = 0; i < englishNumbers.size(); i++)
            temp = temp.replaceAll(arabicNumbers.get(i), englishNumbers.get(i));

        return temp;
    }
}
