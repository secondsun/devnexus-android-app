package org.devnexus.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public abstract class JsonHandler<T> {

    static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json,
                                            Type typeOfT,
                                            JsonDeserializationContext jsonDeserializationContext)
                            throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });

        gson = gsonBuilder.create();
    }

    public abstract List<T> parse(String response);

}
