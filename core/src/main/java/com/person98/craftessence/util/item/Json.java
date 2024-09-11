package com.person98.craftessence.util.item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {
    private static final Gson gson = new GsonBuilder().create();

    public static Gson getGson() {
        return gson;
    }
}