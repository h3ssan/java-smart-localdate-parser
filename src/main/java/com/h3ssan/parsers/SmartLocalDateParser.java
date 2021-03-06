package com.h3ssan.parsers;

import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SmartLocalDateParser {
    private final String date;

    private final String delimiter;

    private int year, month, day;

    /**
     * Possible delimiters that maybe used in the date, and SmartLocalDateParser
     * will identify and parse them
     */
    private final List<String> possibleDelimiters = new ArrayList<>(Arrays.asList("-", "/", "_"));

    /**
     * Constructor that performing all the required steps to make sure the date String
     * is clean and has a valid syntax
     *
     * @param date String represents the date that will be converted into LocalDate
     * @throws DateTimeException
     */
    public SmartLocalDateParser(@NotNull String date) throws DateTimeException {
        this.date = preProcessing(date);

        this.delimiter = extractDelimiter();
        if (this.delimiter == null)
            throw new DateTimeException("Invalid date format, should be in yyyy-mm-dd OR dd-mm-yyyy format");

        extractYearMonthAndDay();
    }

    /**
     * Used to convert year, month and day variables into the LocalDate and return it
     *
     * @return LocalDate which is the final journey in our class and
     * what we need
     * @throws DateTimeException if year, month or day invalid number
     */
    public LocalDate getLocalDate() throws DateTimeException {
        return LocalDate.of(year, month, day);
    }

    /**
     * A method which responsible for filtering the date parameter and make sure
     * that the passed String date is clean
     *
     * @param  date  a String which represents a date
     * @return       a Cleaned String date which is the final modification of the date
     * @see          String
     */
    private String preProcessing(@NotNull String date) {
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
    private String replaceArabicNumbers(@NotNull String date) {
        List<String> arabicNumbers = new ArrayList<>(Arrays.asList("٠", "١", "٢", "٣" ,"٤", "٥", "٦", "٧", "٨", "٩"));
        List<String> englishNumbers = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        String temp = date;

        for (int i = 0; i < englishNumbers.size(); i++)
            temp = temp.replaceAll(arabicNumbers.get(i), englishNumbers.get(i));

        return temp;
    }

    /**
     * A method that matches a regular expression on the final date
     *
     * @param pattern a pattern that used to check on
     * @return boolean represents whatever the pattern matched or not
     * @see Pattern
     */
    private boolean expressionMatch(@NotNull Pattern pattern) {
        return pattern.matcher(this.date).matches();
    }

    /**
     * method used to extract the delimiter in the date String, as well as check
     * if the date String has a valid syntax
     *
     * @return String if the delimiter is extracted and date format looks good,
     * and null if there's an invalid format or multiple delimiters used
     */
    private String extractDelimiter() {
        /*
        * temp1 for YYYY-MM-DD and temp2 for DD-MM-YYYY
        */
        String temp, temp2;

        for (String delimiter : possibleDelimiters) {
            temp = String.format("^([0-9]{4})%s([0-9]{1,2})%s([0-9]{1,2})$", delimiter, delimiter);
            temp2 = String.format("^([0-9]{1,2})%s([0-9]{1,2})%s([0-9]{4})$", delimiter, delimiter);

            if (expressionMatch(Pattern.compile(temp)) || expressionMatch(Pattern.compile(temp2)))
                return delimiter;
        }

        return null;
    }

    /**
     * Method that used to extract year, month and day from the date String
     * into the final variables in the class scope
     */
    private void extractYearMonthAndDay() {
        String[] temp = date.split(delimiter);

        // Month is always in the middle
        this.month = Integer.parseInt(temp[1]);

        // The rest, year and day.
        if (temp[0].length() == 4) {
            this.year = Integer.parseInt(temp[0]);
            this.day = Integer.parseInt(temp[2]);
        } else {
            this.year = Integer.parseInt(temp[2]);
            this.day = Integer.parseInt(temp[0]);
        }
    }
}
