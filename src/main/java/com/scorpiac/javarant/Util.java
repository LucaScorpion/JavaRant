package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class Util {
    private Util() {
    }

    static <T> List<T> jsonToList(JsonArray json, Function<JsonElement, T> converter) {
        List<T> result = new ArrayList<>(json.size());
        json.forEach(j -> result.add(converter.apply(j)));
        return result;
    }

    /**
     * Check whether the JSON is not null and the success member is true.
     *
     * @param json The JSON to check.
     * @return True if the JSON is not null and has a success member which is true.
     */
    static boolean jsonSuccess(JsonObject json) {
        return json != null && json.get("success").getAsBoolean();
    }
}
