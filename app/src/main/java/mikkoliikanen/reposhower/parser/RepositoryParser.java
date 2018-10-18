package mikkoliikanen.reposhower.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import mikkoliikanen.reposhower.model.Repository;

public class RepositoryParser implements ResponseParser<Repository> {

    public Repository parse(JSONObject repositoryObject) throws JSONException {

        String name = getNullableString(repositoryObject, "name");

        final JSONObject ownerObject = repositoryObject.getJSONObject("owner");
        String ownerLogin = ownerObject != null ? getNullableString(ownerObject, "login") : null;

        String htmlUrl = getNullableString(repositoryObject, "html_url");
        String description = getNullableString(repositoryObject, "description");
        String language = getNullableString(repositoryObject, "language");

        Date lastPushAt = TimestampParser.parse(getNullableString(repositoryObject, "pushed_at"));
        Date createdAt = TimestampParser.parse(getNullableString(repositoryObject,"created_at"));

        final JSONObject licenseObject = !repositoryObject.isNull("license") ? repositoryObject.getJSONObject("license") : null;
        String licenseName = licenseObject != null ? getNullableString(licenseObject, "name") : null;

        return new Repository(
                name,
                ownerLogin,
                htmlUrl,
                description,
                language,
                licenseName,
                lastPushAt,
                createdAt
        );


    }

    private String getNullableString(JSONObject repositoryObject, String field) throws JSONException {
        return repositoryObject.isNull(field) ? null : repositoryObject.getString(field);
    }

}
