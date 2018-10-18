package mikkoliikanen.reposhower;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mikkoliikanen.reposhower.model.Repository;

class RepositoriesFromCloudLoader extends AsyncTaskLoader<List<Repository>> {

    private final String username;

    public RepositoriesFromCloudLoader(Context context, String username) {
        super(context);
        this.username = username;
    }

    @Override
    public List<Repository> loadInBackground() {
        Log.d("RepoCloudLoader", "loading repositories for " + username);

        return Arrays.asList(
                new Repository(
                        "foobar",
                        "zifban",
                        "https://github.com/octocat/Hello-World",
                        "description for foobar",
                        null,
                        null,
                        new Date(),
                        new Date()
                ),

                new Repository(
                        "zip-zap-zooey",
                        "zifban",
                        "https://github.com/octocat/Hello-World",
                        "description for zip zap zooey",
                        "Hoplop",
                        "MIT License",
                        new Date(),
                        new Date()
                )
        );
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
