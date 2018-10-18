package mikkoliikanen.reposhower;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.LogDomain;
import com.couchbase.lite.LogLevel;

import java.util.List;
import java.util.Map;

import mikkoliikanen.reposhower.loader.CommitsFromCloudLoader;
import mikkoliikanen.reposhower.loader.RepositoriesFromCloudLoader;
import mikkoliikanen.reposhower.model.Commit;
import mikkoliikanen.reposhower.model.Repository;
import mikkoliikanen.reposhower.storage.RepositoryConverter;
import mikkoliikanen.reposhower.storage.RepositoryStorage;
import mikkoliikanen.reposhower.view.RepositoryListAdapter;

public class RepoListingActivity extends Activity implements LoaderManager.LoaderCallbacks{

    private static final String TAG = "RepoListingActivity";

    private static final String USERNAME = "keletappi";

    private static final int LOADER_REPOS_FROM_CLOUD = 1;
    private static final int LOADER_COMMITS_FROM_CLOUD = 2;
    private static final int LOADER_REPOS_FROM_DB = 3;

    private static final String ARG_NAME = "name";
    private static final String ARG_USERNAMES = "usernames";
    private static final String ARG_REPOSITORIES = "repositories";

    private ListView repoList;

    private RepositoryStorage repositoryStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
            Database database = new Database("mydb", config);
            Database.setLogLevel(LogDomain.REPLICATOR, LogLevel.VERBOSE);
            Database.setLogLevel(LogDomain.QUERY, LogLevel.VERBOSE);
            repositoryStorage = new RepositoryStorage(database, new RepositoryConverter());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_repo_listing);
        repoList = findViewById(R.id.repo_list);

        loadRepositoriesFromDb(USERNAME);
        loadRepositoriesFromCloud(USERNAME);
    }

    private void loadRepositoriesFromCloud(String username) {
        final LoaderManager loaderManager = getLoaderManager();
        final Loader<List<Repository>> loader = loaderManager.getLoader(LOADER_REPOS_FROM_CLOUD);
        Bundle args = new Bundle();
        args.putString(ARG_NAME, username);
        if (loader == null) {
            loaderManager.initLoader(LOADER_REPOS_FROM_CLOUD, args, this);
        } else {
            loaderManager.restartLoader(LOADER_REPOS_FROM_CLOUD, args, this);
        }
    }

    private void loadRepositoriesFromDb(String username) {
        final LoaderManager loaderManager = getLoaderManager();
        final Loader<List<Repository>> loader = loaderManager.getLoader(LOADER_REPOS_FROM_DB);
        Bundle args = new Bundle();
        args.putString(ARG_NAME, username);
        if (loader == null) {
            loaderManager.initLoader(LOADER_REPOS_FROM_DB, args, this);
        } else {
            loaderManager.restartLoader(LOADER_REPOS_FROM_DB, args, this);
        }
    }



    private void loadCommitsFromCloud(List<Repository> data) {
        final LoaderManager loaderManager = getLoaderManager();
        Bundle args = new Bundle();
        String[] usernames = new String[data.size()];
        String[] repositories = new String[data.size()];
        for (int i=0 ; i < data.size() ; i++) {
            repositories[i] = data.get(i).getName();
            usernames[i] = data.get(i).getOwnerLogin();
        }
        args.putStringArray(ARG_USERNAMES, usernames);
        args.putStringArray(ARG_REPOSITORIES, repositories);
        final Loader<List<Commit>> loader = loaderManager.getLoader(LOADER_COMMITS_FROM_CLOUD);
        if (loader == null) {
            loaderManager.initLoader(LOADER_COMMITS_FROM_CLOUD, args, this);
        } else {
            loaderManager.restartLoader(LOADER_COMMITS_FROM_CLOUD, args, this);
        }
    }



    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_REPOS_FROM_CLOUD:
                return new RepositoriesFromCloudLoader(this, args.getString(ARG_NAME), repositoryStorage);
            case LOADER_COMMITS_FROM_CLOUD:
                final String[] usernames = args.getStringArray(ARG_USERNAMES);
                final String[] repositories = args.getStringArray(ARG_REPOSITORIES);
                final CommitsFromCloudLoader.Param[] params = new CommitsFromCloudLoader.Param[usernames.length];
                for (int i=0 ; i < usernames.length ; i++) {
                    params[i] = new CommitsFromCloudLoader.Param(repositories[i], usernames[i]);
                }
                return new CommitsFromCloudLoader(this, params);
            case LOADER_REPOS_FROM_DB:
                return new RepositoriesFromDbLoader(this, args.getString(ARG_NAME), repositoryStorage);
        }
        throw new RuntimeException("onCreateLoader - Unknown loader ID " + id);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        final int id = loader.getId();
        switch (id) {
            case LOADER_REPOS_FROM_CLOUD:
                onRepositoryStorageChanged();
                return;
            case LOADER_COMMITS_FROM_CLOUD:
                onCommitsLoaded((Map<String, Commit>) data);
                return;
            case LOADER_REPOS_FROM_DB:
                onRepositoriesLoaded((List<Repository>) data);
                return;
        }
        throw new RuntimeException("onLoadFinished - Unknown loader ID " + id);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void onRepositoryStorageChanged() {
        loadRepositoriesFromDb(USERNAME);
    }

    private void onRepositoriesLoaded(List<Repository> data) {
        RepositoryListAdapter adapter = (RepositoryListAdapter) repoList.getAdapter();
        repoList.setAdapter(new RepositoryListAdapter(data));
        loadCommitsFromCloud(data);
    }

    private void onCommitsLoaded(Map<String, Commit> data) {
        RepositoryListAdapter adapter = (RepositoryListAdapter) repoList.getAdapter();
        if (adapter != null) {
            adapter.setCommits(data);
            repoList.invalidate();
        }

    }


}
