
import com.github.lgooddatepicker.optionalusertools.*;
import java.time.LocalDate;

/**
 * This reference was used during the implementation of this class.
 * Specifically, Line 1071 was looked at.
 * https://github.com/LGoodDatePicker/LGoodDatePicker/blob/master/Project/src/main/java/com/github/lgooddatepicker/demo/FullDemo.java
 */

/**
 * The <code>ExcludeDatesInPastPolicy</code> class is used for limiting the range of dates available in the date picker.
 * In this case, dates in the past will not be selectable from the date picker.
 */
public class ExcludeDatesInPastPolicy implements DateVetoPolicy {
    @Override
    public boolean isDateAllowed(LocalDate date) {
        //Today's date
        LocalDate now = LocalDate.now();
        //This will prohibit the selection of a date in the past for deadline.
        if (date.isBefore(now)) {
            return false;
        }
        return true;
    }
}

