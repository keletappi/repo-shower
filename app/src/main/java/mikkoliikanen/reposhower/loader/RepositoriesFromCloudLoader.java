package mikkoliikanen.reposhower.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import mikkoliikanen.reposhower.model.Repository;
import mikkoliikanen.reposhower.parser.RepositoriesResponseParser;
import mikkoliikanen.reposhower.parser.RepositoryParser;

import static android.content.ContentValues.TAG;

public class RepositoriesFromCloudLoader extends AsyncTaskLoader<List<Repository>> {
    private static final String URL_PATTERN = "https://api.github.com/users/%s/repos";

    private final String username;

    public RepositoriesFromCloudLoader(Context context, String username) {
        super(context);
        this.username = username;
    }

    @Override
    public List<Repository> loadInBackground() {
        Log.d("RepoCloudLoader", "loading repositories for " + username);

        final String formattedUrl = String.format(URL_PATTERN, username);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(formattedUrl).openConnection();
            int resultCode = connection.getResponseCode();
            if (resultCode == 200) {
                return new RepositoriesResponseParser(new RepositoryParser()).parse(readBody(connection));
            } else {
                Log.w(TAG, "Non-OK response for '" + formattedUrl + "'");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Bad URL '" + formattedUrl + "'", e);
        } catch (IOException e) {
            Log.w(TAG, "Failed loading repos from '" + formattedUrl + "'", e);
        } catch (JSONException e) {
            Log.w(TAG, "Failed parsing repos", e);
        }

        return Collections.emptyList();

    }

    private String readBody(HttpURLConnection connection) {
        try (InputStream in = connection.getInputStream()) {
            java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            Log.w(TAG, "failed reading response body", e);
        }
        return "";
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
