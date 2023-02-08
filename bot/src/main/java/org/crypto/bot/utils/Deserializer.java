package org.crypto.bot.utils;

import com.google.gson.Gson;

/**
 * Used to deserialize the JSON response from the Exchange's API to the correct Entity.
 */
public class Deserializer {
    /**
     * Deserializes a json string and creates the given entity.
     * @param json the json string
     * @param objectClass the class representing the entity
     * @return the created object
     * @param <T> the type parameter of the class representing the entity
     */
    public static <T> T deserialize(String json, Class<T> objectClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, objectClass);
    }
}
