package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

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
}
