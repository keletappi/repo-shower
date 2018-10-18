package mikkoliikanen.reposhower.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mikkoliikanen.reposhower.model.Commit;
import mikkoliikanen.reposhower.parser.ArrayResponseParser;
import mikkoliikanen.reposhower.parser.CommitParser;

public class CommitsFromCloudLoader extends AsyncTaskLoader<Map<String, Commit>> {

    private static final String URL_PATTERN = "https://api.github.com/repos/%s/%s/commits";

    private static final String TAG = "CommitCloudLoader";


    private final List<Param> params;

    public static class Param {
        private final String repository;
        private final String owner;

        public Param(String repository, String owner) {
            this.repository = repository;
            this.owner = owner;
        }
    }

    public CommitsFromCloudLoader(Context context, Param ... params) {
        super(context);
        this.params = Arrays.asList(params);
    }

    @Override
    public Map<String, Commit> loadInBackground() {
        Map<String, Commit> result = new HashMap<>();
        for (Param param : params) {
            String formattedUrl = formattedUrl(param);
            try {
                URL url = new URL(formattedUrl);
                Log.d(TAG, "Loading commits from '" + url.toString() + "'");

                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                final int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    List<Commit> commits = new ArrayResponseParser<>(new CommitParser()).parse(ConnectionUtils.readBody(connection));
                    result.put(param.repository, commits.isEmpty() ? null : commits.get(0));
                } else {
                    Log.w(TAG, "Non-OK response " + responseCode + "-" + connection.getResponseMessage() +
                            " for '" + formattedUrl + "'");
                }
            } catch (IOException e) {
                Log.w(TAG, "Failed loading commits from '" + formattedUrl + "'", e);
            } catch (JSONException e) {
                Log.w(TAG, "Failed parsing commits from '" + formattedUrl + "'", e);
            }
        }
        return result;
    }

    private String formattedUrl(Param param) {
        return String.format(URL_PATTERN, param.owner, param.repository);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
