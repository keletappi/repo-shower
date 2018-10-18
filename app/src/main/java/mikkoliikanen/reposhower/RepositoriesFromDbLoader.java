package mikkoliikanen.reposhower;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import mikkoliikanen.reposhower.model.Repository;
import mikkoliikanen.reposhower.storage.RepositoryStorage;

class RepositoriesFromDbLoader extends AsyncTaskLoader<List<Repository>> {
    private final String username;
    private final RepositoryStorage repositoryStorage;

    public RepositoriesFromDbLoader(Context context, String username, RepositoryStorage repositoryStorage) {
        super(context);
        this.username = username;
        this.repositoryStorage = repositoryStorage;
    }

    @Override
    public List<Repository> loadInBackground() {
        return repositoryStorage.getAllOwnedBy(username);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
