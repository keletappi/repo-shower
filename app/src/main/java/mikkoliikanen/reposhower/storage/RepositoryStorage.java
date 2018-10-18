package mikkoliikanen.reposhower.storage;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;

import java.util.List;

import mikkoliikanen.reposhower.model.Repository;

public class RepositoryStorage {

    private static final String TAG = "RepositoryStorage";

    private final Database database;
    private final DocumentConverter<Repository> converter;

    public RepositoryStorage(Database database, DocumentConverter<Repository> converter) {
        this.database = database;
        this.converter = converter;
    }

    public void store(List<Repository> repositories) {
        for (Repository repository : repositories) {
            store(repository);
        }
    }

    private void store(Repository repository) {
        try {
            database.save(converter.asDocument(repository));
        } catch (CouchbaseLiteException e) {
            Log.w(TAG, "failed storing repository " + repository.getName());
        }
    }

}
