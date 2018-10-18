package mikkoliikanen.reposhower.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import mikkoliikanen.reposhower.model.Commit;

public class CommitParser implements ResponseParser<Commit> {

    @Override
    public Commit parse(JSONObject object) throws JSONException {
        String sha = object.getString("sha");
        final JSONObject commitObject = object.getJSONObject("commit");
        String message = commitObject.getString("message");
        String author = commitObject.getJSONObject("author").getString("name");
        Date commitDate = TimestampParser.parse(
                commitObject
                        .getJSONObject("author")
                        .getString("date")
        );

        return new Commit(sha, message, author, commitDate);
    }
}
