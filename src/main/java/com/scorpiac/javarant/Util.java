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

    /**
     * Create a list from a JSON array.
     *
     * @param json      The JSON array to convert to a list.
     * @param converter The converter for converting individual JSON elements.
     * @param <T>       The type to convert the elements to.
     * @return A list of elements converted from the JSON.
     */
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
