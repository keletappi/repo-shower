package mikkoliikanen.reposhower.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import mikkoliikanen.reposhower.R;
import mikkoliikanen.reposhower.model.Commit;
import mikkoliikanen.reposhower.model.Repository;

import static android.text.format.DateFormat.*;

class RepositoryPresenter {

    private TextView name;
    private TextView ownerLogin;
    private TextView htmlUrl;
    private TextView description;
    private TextView language;
    private TextView licenseName;
    private TextView createdAt;
    private TextView lastPushAt;

    private TextView commitSha;
    private TextView commitAuthor;
    private TextView commitMessage;
    private TextView commitTimestamp;

    RepositoryPresenter(View view) {
        name = view.findViewById(R.id.name);
        ownerLogin = view.findViewById(R.id.owner_login);
        htmlUrl = view.findViewById(R.id.html_url);
        description = view.findViewById(R.id.description);
        language = view.findViewById(R.id.language);
        licenseName = view.findViewById(R.id.license_name);
        createdAt = view.findViewById(R.id.created_at);
        lastPushAt = view.findViewById(R.id.pushed_at);

        commitSha = view.findViewById(R.id.commit_sha);
        commitAuthor = view.findViewById(R.id.commit_author);
        commitMessage = view.findViewById(R.id.commit_message);
        commitTimestamp = view.findViewById(R.id.commit_timestamp);

    }


    void present(Repository repository, Commit commit) {
        name.setText(repository.getName());
        ownerLogin.setText(repository.getOwnerLogin());
        htmlUrl.setText(repository.getHtmlUrl());

        setDescription(repository.getDescription());
        setLanguage(repository.getLanguage());
        setLicense(repository.getLicenseName());

        setTimestamp(createdAt, repository.getCreatedAt());
        setTimestamp(lastPushAt, repository.getLastPushAt());

        if (commit != null) {
            commitSha.setText(commit.getSha());
            commitAuthor.setText(commit.getAuthor());
            commitMessage.setText(commit.getMessage());
            setTimestamp(commitTimestamp, commit.getCommitDate());
        } else {
            commitSha.setText(R.string.loading_commit);
            commitAuthor.setText("");
            commitMessage.setText("");
            commitTimestamp.setText("");
        }

    }

    private void setTimestamp(TextView view, Date timestamp) {
        if (timestamp != null) {
            DateFormat dateFormat = getDateFormat(view.getContext());
            DateFormat timeFormat = getTimeFormat(view.getContext());

            view.setText(String.format("%s %s",
                    dateFormat.format(timestamp),
                    timeFormat.format(timestamp)));

        } else {
            view.setText(R.string.never);
        }

    }

    private void setLicense(String licenseNameText) {
        if (!TextUtils.isEmpty(licenseNameText)) {
            licenseName.setText(licenseNameText);
        } else {
            licenseName.setText(R.string.no_license);
        }
    }

    private void setLanguage(String languageText) {
        if (!TextUtils.isEmpty(languageText)) {
            language.setText(languageText);
        } else {
            language.setText(R.string.no_language);
        }
    }

    private void setDescription(String descriptionText) {
        if (!TextUtils.isEmpty(descriptionText)) {
            description.setText(descriptionText);
        } else {
            description.setText(R.string.no_description);
        }
    }
}
