package org.devnexus.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.devnexus.model.Sponsor;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse sponsor JSON
 */
public class SponsorJsonHandler extends JsonHandler<Sponsor> {

    @Override
    public List<Sponsor> parse(String response) {
        List<Sponsor> sponsors = new ArrayList<>();

        JsonElement jsonRootElement = new JsonParser().parse(response);
        JsonArray sponsorsJsonArray = jsonRootElement.getAsJsonObject().getAsJsonArray("sponsors");

        for (int i = 0; i < sponsorsJsonArray.size(); i++) {
            final JsonElement jsonElement = sponsorsJsonArray.get(i);
            final Sponsor sponsor = new Gson().fromJson(jsonElement, Sponsor.class);
            sponsors.add(sponsor);
        }

        return sponsors;
    }

}
