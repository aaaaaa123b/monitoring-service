package by.harlap.monitoring.validator;

import by.harlap.monitoring.exception.GenericHttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator for meter readings monthly information.
 */
@Component
@RequiredArgsConstructor
public class MeterReadingsMonthlyInfoValidator {

    /**
     * Validates the existence of month and year parameters.
     *
     * @param month the month parameter
     * @param year  the year parameter
     * @throws GenericHttpException if either month or year is null
     */
    public void validateMonthAndYearExistence(final String month, final String year) {
        if (month == null || year == null) {
            throw new GenericHttpException("Параметры месяц и год не могут быть пустыми");
        }
    }

    /**
     * Validates the format and range of the month.
     *
     * @param monthString the month string to be validated
     * @return the month as an integer
     * @throws GenericHttpException if the month format is incorrect or out of range
     */
    public int validateMonth(final String monthString) {
        final int month;
        try {
            month = Integer.parseInt(monthString);
        } catch (NumberFormatException e) {
            throw new GenericHttpException("Неправильный формат месяца");
        }
        if (month > 12 || month < 1) {
            throw new GenericHttpException("Месяц должен быть задан в диапазоне от 1 до 12");
        }
        return month;
    }

    /**
     * Validates the format and positivity of the year
     *
     * @param yearString the year string to be validated
     * @return the year as an integer
     * @throws GenericHttpException if the year format is incorrect or negative
     */
    public int validateYear(String yearString) {
        final int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            throw new GenericHttpException("Неправильный формат года");
        }
        if (year < 1) {
            throw new GenericHttpException("Год должен быть положительным числом");
        }
        return year;
    }
}
