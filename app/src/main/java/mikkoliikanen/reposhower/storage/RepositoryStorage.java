package mikkoliikanen.reposhower.storage;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Expression;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.util.ArrayList;
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

    public List<Repository> getAllOwnedBy(String username) {
        List<Repository> repositories = new ArrayList<>();
        try {
            final ResultSet results = QueryBuilder.select(
                    SelectResult.property(RepositoryConverter.NAME))
                    .from(DataSource.database(database))
                    .where(Expression.property("type").equalTo(Expression.string("repository"))
                            .and(Expression.property(RepositoryConverter.OWNER_LOGIN)
                                    .equalTo(Expression.string(username))))
                    .execute();
            final List<Result> resultList = results.allResults();
            Log.d(TAG, "DB query resulted in " + resultList.size() + " items");
            for (Result result : resultList) {
                final Document repositoryDocument = database.getDocument(result.getString(RepositoryConverter.NAME));
                repositories.add(converter.asObject(repositoryDocument));
            }
        } catch (CouchbaseLiteException e) {
            Log.w(TAG, "Failed reading repositories", e);
        }

        return repositories;
    }
}
