package mikkoliikanen.reposhower;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import mikkoliikanen.reposhower.loader.RepositoriesFromCloudLoader;
import mikkoliikanen.reposhower.model.Repository;
import mikkoliikanen.reposhower.view.RepositoryListAdapter;

public class RepoListingActivity extends Activity implements LoaderManager.LoaderCallbacks{

    private static final String USERNAME = "keletappi";

    private static final int LOADER_REPOS_FROM_CLOUD = 1;

    private static final String ARG_NAME = "name";

    private ListView repoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_repo_listing);
        repoList = findViewById(R.id.repo_list);

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

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new RepositoriesFromCloudLoader(this, args.getString(ARG_NAME));
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        onRepositoriesLoaded((List<Repository>) data);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void onRepositoriesLoaded(List<Repository> data) {
        repoList.setAdapter(new RepositoryListAdapter(data));
        // TODO: trigger commit loading
    }





}
