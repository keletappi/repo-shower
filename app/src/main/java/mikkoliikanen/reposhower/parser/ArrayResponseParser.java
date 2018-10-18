package mikkoliikanen.reposhower.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ArrayResponseParser<T> {

    private ResponseParser<T> repositoryParser;

    public ArrayResponseParser(ResponseParser<T> parser) {
        this.repositoryParser = parser;
    }

    public List<T> parse(String apiResponse) throws JSONException {
        List<T> result = new ArrayList<>();
        JSONArray response = (JSONArray) new JSONTokener(apiResponse).nextValue();
        for (int i=0 ; i < response.length() ; i++) {
            result.add(repositoryParser.parse((JSONObject) response.get(i)));
        }

        return result;

    }

}
