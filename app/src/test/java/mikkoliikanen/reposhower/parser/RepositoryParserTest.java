package mikkoliikanen.reposhower.parser;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import mikkoliikanen.reposhower.model.Repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(RobolectricTestRunner.class)
public class RepositoryParserTest {

    // JSON copied from github api doc:
    private static final String TEST_REPOSITORY_JSON = "  {\n" +
                    "    \"id\": 1296269,\n" +
                    "    \"node_id\": \"MDEwOlJlcG9zaXRvcnkxMjk2MjY5\",\n" +
                    "    \"name\": \"Hello-World\",\n" +
                    "    \"full_name\": \"octocat/Hello-World\",\n" +
                    "    \"owner\": {\n" +
                    "      \"login\": \"octocat\",\n" +
                    "      \"id\": 1,\n" +
                    "      \"node_id\": \"MDQ6VXNlcjE=\",\n" +
                    "      \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\n" +
                    "      \"gravatar_id\": \"\",\n" +
                    "      \"url\": \"https://api.github.com/users/octocat\",\n" +
                    "      \"html_url\": \"https://github.com/octocat\",\n" +
                    "      \"followers_url\": \"https://api.github.com/users/octocat/followers\",\n" +
                    "      \"following_url\": \"https://api.github.com/users/octocat/following{/other_user}\",\n" +
                    "      \"gists_url\": \"https://api.github.com/users/octocat/gists{/gist_id}\",\n" +
                    "      \"starred_url\": \"https://api.github.com/users/octocat/starred{/owner}{/repo}\",\n" +
                    "      \"subscriptions_url\": \"https://api.github.com/users/octocat/subscriptions\",\n" +
                    "      \"organizations_url\": \"https://api.github.com/users/octocat/orgs\",\n" +
                    "      \"repos_url\": \"https://api.github.com/users/octocat/repos\",\n" +
                    "      \"events_url\": \"https://api.github.com/users/octocat/events{/privacy}\",\n" +
                    "      \"received_events_url\": \"https://api.github.com/users/octocat/received_events\",\n" +
                    "      \"type\": \"User\",\n" +
                    "      \"site_admin\": false\n" +
                    "    },\n" +
                    "    \"private\": false,\n" +
                    "    \"html_url\": \"https://github.com/octocat/Hello-World\",\n" +
                    "    \"description\": \"This your first repo!\",\n" +
                    "    \"fork\": true,\n" +
                    "    \"url\": \"https://api.github.com/repos/octocat/Hello-World\",\n" +
                    "    \"archive_url\": \"http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}\",\n" +
                    "    \"assignees_url\": \"http://api.github.com/repos/octocat/Hello-World/assignees{/user}\",\n" +
                    "    \"blobs_url\": \"http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}\",\n" +
                    "    \"branches_url\": \"http://api.github.com/repos/octocat/Hello-World/branches{/branch}\",\n" +
                    "    \"collaborators_url\": \"http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}\",\n" +
                    "    \"comments_url\": \"http://api.github.com/repos/octocat/Hello-World/comments{/number}\",\n" +
                    "    \"commits_url\": \"http://api.github.com/repos/octocat/Hello-World/commits{/sha}\",\n" +
                    "    \"compare_url\": \"http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}\",\n" +
                    "    \"contents_url\": \"http://api.github.com/repos/octocat/Hello-World/contents/{+path}\",\n" +
                    "    \"contributors_url\": \"http://api.github.com/repos/octocat/Hello-World/contributors\",\n" +
                    "    \"deployments_url\": \"http://api.github.com/repos/octocat/Hello-World/deployments\",\n" +
                    "    \"downloads_url\": \"http://api.github.com/repos/octocat/Hello-World/downloads\",\n" +
                    "    \"events_url\": \"http://api.github.com/repos/octocat/Hello-World/events\",\n" +
                    "    \"forks_url\": \"http://api.github.com/repos/octocat/Hello-World/forks\",\n" +
                    "    \"git_commits_url\": \"http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}\",\n" +
                    "    \"git_refs_url\": \"http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}\",\n" +
                    "    \"git_tags_url\": \"http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}\",\n" +
                    "    \"git_url\": \"git:github.com/octocat/Hello-World.git\",\n" +
                    "    \"issue_comment_url\": \"http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}\",\n" +
                    "    \"issue_events_url\": \"http://api.github.com/repos/octocat/Hello-World/issues/events{/number}\",\n" +
                    "    \"issues_url\": \"http://api.github.com/repos/octocat/Hello-World/issues{/number}\",\n" +
                    "    \"keys_url\": \"http://api.github.com/repos/octocat/Hello-World/keys{/key_id}\",\n" +
                    "    \"labels_url\": \"http://api.github.com/repos/octocat/Hello-World/labels{/name}\",\n" +
                    "    \"languages_url\": \"http://api.github.com/repos/octocat/Hello-World/languages\",\n" +
                    "    \"merges_url\": \"http://api.github.com/repos/octocat/Hello-World/merges\",\n" +
                    "    \"milestones_url\": \"http://api.github.com/repos/octocat/Hello-World/milestones{/number}\",\n" +
                    "    \"notifications_url\": \"http://api.github.com/repos/octocat/Hello-World/notifications{?since,all,participating}\",\n" +
                    "    \"pulls_url\": \"http://api.github.com/repos/octocat/Hello-World/pulls{/number}\",\n" +
                    "    \"releases_url\": \"http://api.github.com/repos/octocat/Hello-World/releases{/id}\",\n" +
                    "    \"ssh_url\": \"git@github.com:octocat/Hello-World.git\",\n" +
                    "    \"stargazers_url\": \"http://api.github.com/repos/octocat/Hello-World/stargazers\",\n" +
                    "    \"statuses_url\": \"http://api.github.com/repos/octocat/Hello-World/statuses/{sha}\",\n" +
                    "    \"subscribers_url\": \"http://api.github.com/repos/octocat/Hello-World/subscribers\",\n" +
                    "    \"subscription_url\": \"http://api.github.com/repos/octocat/Hello-World/subscription\",\n" +
                    "    \"tags_url\": \"http://api.github.com/repos/octocat/Hello-World/tags\",\n" +
                    "    \"teams_url\": \"http://api.github.com/repos/octocat/Hello-World/teams\",\n" +
                    "    \"trees_url\": \"http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}\",\n" +
                    "    \"clone_url\": \"https://github.com/octocat/Hello-World.git\",\n" +
                    "    \"mirror_url\": \"git:git.example.com/octocat/Hello-World\",\n" +
                    "    \"hooks_url\": \"http://api.github.com/repos/octocat/Hello-World/hooks\",\n" +
                    "    \"svn_url\": \"https://svn.github.com/octocat/Hello-World\",\n" +
                    "    \"homepage\": \"https://github.com\",\n" +
                    "    \"language\": null,\n" +
                    "    \"forks_count\": 9,\n" +
                    "    \"stargazers_count\": 80,\n" +
                    "    \"watchers_count\": 80,\n" +
                    "    \"size\": 108,\n" +
                    "    \"default_branch\": \"master\",\n" +
                    "    \"open_issues_count\": 0,\n" +
                    "    \"topics\": [\n" +
                    "      \"octocat\",\n" +
                    "      \"atom\",\n" +
                    "      \"electron\",\n" +
                    "      \"API\"\n" +
                    "    ],\n" +
                    "    \"has_issues\": true,\n" +
                    "    \"has_projects\": true,\n" +
                    "    \"has_wiki\": true,\n" +
                    "    \"has_pages\": false,\n" +
                    "    \"has_downloads\": true,\n" +
                    "    \"archived\": false,\n" +
                    "    \"pushed_at\": \"2011-01-26T19:06:43Z\",\n" +
                    "    \"created_at\": \"2011-01-26T19:01:12Z\",\n" +
                    "    \"updated_at\": \"2011-01-26T19:14:43Z\",\n" +
                    "    \"permissions\": {\n" +
                    "      \"admin\": false,\n" +
                    "      \"push\": false,\n" +
                    "      \"pull\": true\n" +
                    "    },\n" +
                    "    \"allow_rebase_merge\": true,\n" +
                    "    \"allow_squash_merge\": true,\n" +
                    "    \"allow_merge_commit\": true,\n" +
                    "    \"subscribers_count\": 42,\n" +
                    "    \"network_count\": 0,\n" +
                    "    \"license\": {\n" +
                    "      \"key\": \"mit\",\n" +
                    "      \"name\": \"MIT License\",\n" +
                    "      \"spdx_id\": \"MIT\",\n" +
                    "      \"url\": \"https://api.github.com/licenses/mit\",\n" +
                    "      \"node_id\": \"MDc6TGljZW5zZW1pdA==\"\n" +
                    "    }\n" +
                    "  }";

    // 2011-01-26T19:01:12Z (createdAt in above JSON)
    private static final Date EXPECTED_CREATED_AT = new Date(1296068472000L);

    // 2011-01-26T19:06:43Z (pushedAt in above JSON)
    private static final Date EXPECTED_PUSHED_AT = new Date(1296068803000L);

    @Test
    public void testParse() throws Exception {
        RepositoryParser parser = new RepositoryParser();
        Repository result = parser.parse(new JSONObject(TEST_REPOSITORY_JSON));
        assertThat(result.getName(), is("Hello-World"));
        assertThat(result.getOwnerLogin(), is("octocat"));
        assertThat(result.getHtmlUrl(), is("https://github.com/octocat/Hello-World"));
        assertThat(result.getDescription(), is("This your first repo!"));
        assertThat(result.getLicenseName(), is("MIT License"));

        assertThat(result.getLanguage(), is(nullValue()));

        assertThat(result.getCreatedAt(), is(EXPECTED_CREATED_AT));
        assertThat(result.getLastPushAt(), is(EXPECTED_PUSHED_AT));


    }
}