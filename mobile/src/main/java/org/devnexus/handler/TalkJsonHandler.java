package org.devnexus.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.devnexus.model.Talk;

import java.util.ArrayList;
import java.util.List;

public class TalkJsonHandler extends JsonHandler<Talk> {

    @Override
    public List<Talk> parse(String response) {
        List<Talk> talks = new ArrayList<>();

        JsonElement jsonRootElement = new JsonParser().parse(response);
        JsonArray scheduleItemsJsonArray = jsonRootElement.getAsJsonObject()
                .getAsJsonArray("scheduleItems");

        for (int i = 0; i < scheduleItemsJsonArray.size(); i++) {
            final JsonElement jsonElement = scheduleItemsJsonArray.get(i);
            final Talk talk = gson.fromJson(jsonElement, Talk.class);
            talks.add(talk);
        }

        return talks;
    }

}
