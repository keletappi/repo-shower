package mikkoliikanen.reposhower.parser;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class TimestampParser {

    private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US);

    /** Parses ISO8601 timestamp (in the form github apis use) */
    static Date parse(String text) {
        try {
            return TextUtils.isEmpty(text) ? null : DATE_PARSER.parse(text);
        } catch (ParseException e) {
            Log.w("TimestampParser", "Failed parsing date '" + text + "'", e);
            return null;
        }
    }
}
