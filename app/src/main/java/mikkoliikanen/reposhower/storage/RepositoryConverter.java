package mikkoliikanen.reposhower.storage;

import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;

import mikkoliikanen.reposhower.model.Repository;

public class RepositoryConverter implements DocumentConverter<Repository> {
    private static final String NAME = "name";
    private static final String OWNER_LOGIN = "owner_login";
    private static final String HTML_URL = "html_url";
    private static final String DESCRIPTION = "description";
    private static final String LANGUAGE = "language";
    private static final String LICENSE_NAME = "license_name";
    private static final String CREATED_AT = "created_at";
    private static final String LAST_PUSH_AT = "last_push_at";


    @Override
    public MutableDocument asDocument(Repository repository) {
        MutableDocument document = new MutableDocument();

        document.setString("type", "repository");

        document.setString(NAME, repository.getName());
        document.setString(OWNER_LOGIN, repository.getOwnerLogin());
        document.setString(HTML_URL, repository.getHtmlUrl());
        document.setString(DESCRIPTION, repository.getDescription());
        document.setString(LANGUAGE, repository.getLanguage());
        document.setString(LICENSE_NAME, repository.getLicenseName());

        document.setDate(CREATED_AT, repository.getCreatedAt());
        document.setDate(LAST_PUSH_AT, repository.getLastPushAt());

        return document;
    }

    @Override
    public Repository asObject(Document document) {
        // TODO: Convert stored object to POJO
        return null;
    }
}
