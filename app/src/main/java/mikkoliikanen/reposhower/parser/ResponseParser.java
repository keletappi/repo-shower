package mikkoliikanen.reposhower.parser;

import org.json.JSONException;
import org.json.JSONObject;

interface ResponseParser<T> {
    T parse(JSONObject json) throws JSONException;
}
