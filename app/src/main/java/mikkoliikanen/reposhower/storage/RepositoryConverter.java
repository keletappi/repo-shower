package mikkoliikanen.reposhower.storage;

import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;

import java.util.Date;

import mikkoliikanen.reposhower.model.Repository;

public class RepositoryConverter implements DocumentConverter<Repository> {
    static final String NAME = "name";
    static final String OWNER_LOGIN = "owner_login";
    static final String HTML_URL = "html_url";
    static final String DESCRIPTION = "description";
    static final String LANGUAGE = "language";
    static final String LICENSE_NAME = "license_name";
    static final String CREATED_AT = "created_at";
    static final String LAST_PUSH_AT = "last_push_at";


    @Override
    public MutableDocument asDocument(Repository repository) {
        MutableDocument document = new MutableDocument(repository.getName());

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
        String name = document.getString(NAME);
        String ownerLogin = document.getString(OWNER_LOGIN);
        String htmlUrl = document.getString(HTML_URL);
        String description = document.getString(DESCRIPTION);
        String language = document.getString(LANGUAGE);
        String licenseName = document.getString(LICENSE_NAME);

        Date createdAt = document.getDate(CREATED_AT);
        Date lastPushAt = document.getDate(LAST_PUSH_AT);

        return new Repository(name,
                ownerLogin,
                htmlUrl,
                description,
                language,
                licenseName,
                lastPushAt,
                createdAt);
    }
}
