package com.binance.trader.utils;

import com.google.gson.Gson;

public class Deserializer {
    public static <T> T deserialize(String json, Class<T> objectClass) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, objectClass);
        return object;
    }
}
