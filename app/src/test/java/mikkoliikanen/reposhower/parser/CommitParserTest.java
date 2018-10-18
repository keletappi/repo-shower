package mikkoliikanen.reposhower.parser;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import mikkoliikanen.reposhower.model.Commit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class CommitParserTest {

    private static final String TEST_JSON = "  {\n" +
            "    \"url\": \"https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e\",\n" +
            "    \"sha\": \"6dcb09b5b57875f334f61aebed695e2e4193db5e\",\n" +
            "    \"node_id\": \"MDY6Q29tbWl0NmRjYjA5YjViNTc4NzVmMzM0ZjYxYWViZWQ2OTVlMmU0MTkzZGI1ZQ==\",\n" +
            "    \"html_url\": \"https://github.com/octocat/Hello-World/commit/6dcb09b5b57875f334f61aebed695e2e4193db5e\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e/comments\",\n" +
            "    \"commit\": {\n" +
            "      \"url\": \"https://api.github.com/repos/octocat/Hello-World/git/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e\",\n" +
            "      \"author\": {\n" +
            "        \"name\": \"Monalisa Octocat\",\n" +
            "        \"email\": \"support@github.com\",\n" +
            "        \"date\": \"2011-04-14T16:00:49Z\"\n" +
            "      },\n" +
            "      \"committer\": {\n" +
            "        \"name\": \"Monalisa Octocat\",\n" +
            "        \"email\": \"support@github.com\",\n" +
            "        \"date\": \"2011-04-14T16:00:49Z\"\n" +
            "      },\n" +
            "      \"message\": \"Fix all the bugs\",\n" +
            "      \"tree\": {\n" +
            "        \"url\": \"https://api.github.com/repos/octocat/Hello-World/tree/6dcb09b5b57875f334f61aebed695e2e4193db5e\",\n" +
            "        \"sha\": \"6dcb09b5b57875f334f61aebed695e2e4193db5e\"\n" +
            "      },\n" +
            "      \"comment_count\": 0,\n" +
            "      \"verification\": {\n" +
            "        \"verified\": false,\n" +
            "        \"reason\": \"unsigned\",\n" +
            "        \"signature\": null,\n" +
            "        \"payload\": null\n" +
            "      }\n" +
            "    },\n" +
            "    \"author\": {\n" +
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
            "    \"committer\": {\n" +
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
            "    \"parents\": [\n" +
            "      {\n" +
            "        \"url\": \"https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e\",\n" +
            "        \"sha\": \"6dcb09b5b57875f334f61aebed695e2e4193db5e\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }";

    @Test
    public void testParse() throws Exception {
        final Commit result = new CommitParser().parse(new JSONObject(TEST_JSON));
        assertThat(result.getAuthor(), is("Monalisa Octocat"));
        assertThat(result.getCommitDate(), is(new Date(1302796849000L)));
        assertThat(result.getMessage(), is("Fix all the bugs"));
        assertThat(result.getSha(), is("6dcb09b5b57875f334f61aebed695e2e4193db5e"));
    }
}