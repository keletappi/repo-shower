package mikkoliikanen.reposhower.loader;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static android.content.ContentValues.TAG;

class ConnectionUtils {
    public static String readBody(HttpURLConnection connection) {
        try (InputStream in = connection.getInputStream()) {
            java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            Log.w(TAG, "failed reading response body", e);
        }
        return "";
    }
}
