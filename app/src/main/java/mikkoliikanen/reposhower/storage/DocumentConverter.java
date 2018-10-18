package mikkoliikanen.reposhower.storage;

import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;

interface DocumentConverter<T> {
    MutableDocument asDocument(T object);
    T asObject(Document document);
}
