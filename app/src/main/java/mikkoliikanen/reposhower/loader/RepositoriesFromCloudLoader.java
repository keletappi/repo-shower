package mikkoliikanen.reposhower.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import mikkoliikanen.reposhower.model.Repository;
import mikkoliikanen.reposhower.parser.ArrayResponseParser;
import mikkoliikanen.reposhower.parser.RepositoryParser;
import mikkoliikanen.reposhower.storage.RepositoryStorage;

public class RepositoriesFromCloudLoader extends AsyncTaskLoader<Void> {
    private static final String TAG = "RepoCloudLoader";

    private static final String URL_PATTERN = "https://api.github.com/users/%s/repos";

    private final String username;
    private final RepositoryStorage storage;

    public RepositoriesFromCloudLoader(Context context, String username, RepositoryStorage storage) {
        super(context);
        this.username = username;
        this.storage = storage;
    }

    @Override
    public Void loadInBackground() {
        Log.d("RepoCloudLoader", "loading repositories for " + username);

        final String formattedUrl = String.format(URL_PATTERN, username);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(formattedUrl).openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                final List<Repository> repositories = new ArrayResponseParser<>(new RepositoryParser())
                        .parse(ConnectionUtils.readBody(connection));
                storage.store(repositories);
                return null;
            } else {
                Log.w(TAG, "Non-OK response " + responseCode + "-" + connection.getResponseMessage() +
                        " for '" + formattedUrl + "'");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Bad URL '" + formattedUrl + "'", e);
        } catch (IOException e) {
            Log.w(TAG, "Failed loading repos from '" + formattedUrl + "'", e);
        } catch (JSONException e) {
            Log.w(TAG, "Failed parsing repos", e);
        }

        return null;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
