package mikkoliikanen.reposhower.model;

import java.util.Date;

public class Commit {
    private final String sha;
    private final String message;
    private final String author;
    private final Date commitDate;

    public Commit(String sha,
                  String message,
                  String commitAuthor,
                  Date commitDate) {
        this.sha = sha;
        this.message = message;
        this.author = commitAuthor;
        this.commitDate = commitDate;
    }

    public String getSha() {
        return sha;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCommitDate() {
        return commitDate;
    }
}
