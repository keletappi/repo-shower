package mikkoliikanen.reposhower.model;

import java.util.Date;

public class Repository {

    private final String name;
    private final String ownerLogin;
    private final String htmlUrl;
    private final String description;
    private final String language;
    private final String licenseName;
    private final Date lastPushAt;
    private final Date createdAt;


    public Repository(String name,
                      String ownerLogin,
                      String htmlUrl,
                      String description,
                      String language,
                      String licenseName,
                      Date lastPushAt,
                      Date createdAt) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.htmlUrl = htmlUrl;
        this.description = description;
        this.language = language;
        this.licenseName = licenseName;
        this.lastPushAt = lastPushAt;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public Date getLastPushAt() {
        return lastPushAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
