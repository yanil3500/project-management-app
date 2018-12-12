import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The <code>DateTimeFormattingUtils</code> class is used for converting LocalDate object to String and vice-versa.
 * This is used when a task is presented via the ViewTaskForm.
 * Used this Stackoverflow question when implementing this class.
 */
public class DateTimeFormattingUtils {

    private static final String DATE_PATTERN = "MMMM d, yyyy";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private DateTimeFormattingUtils(){

    }

    public static LocalDate toLocalDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        //Set DateTime locale
        DATE_TIME_FORMATTER.withLocale(Locale.getDefault());
        LocalDate localDate = LocalDate.parse(dateString, DATE_TIME_FORMATTER);
        return localDate;

    }

    public static String dateToString(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(DATE_TIME_FORMATTER);
    }

}
