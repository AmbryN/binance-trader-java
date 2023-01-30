package org.crypto.bot.utils;

import com.google.gson.Gson;

/**
 * Used to deserialize the JSON response from the Binance API to the correct Entity
 */
public class Deserializer {
    public static <T> T deserialize(String json, Class<T> objectClass) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, objectClass);
        return object;
    }
}
