package org.devnexus.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.devnexus.model.Speaker;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpeakerJsonHandler extends JsonHandler<Speaker> {

    @Override
    public List<Speaker> parse(String response) {
        Type listType = new TypeToken<ArrayList<Speaker>>() {
        }.getType();
        List<Speaker> speakers = new Gson().fromJson(response, listType);

        Collections.sort(speakers, new Comparator<Speaker>() {
            @Override
            public int compare(Speaker speaker1, Speaker speaker2) {
                return speaker1.getName().compareTo(speaker2.getName());
            }
        });

        return speakers;
    }

}
