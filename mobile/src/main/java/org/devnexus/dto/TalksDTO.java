package org.devnexus.dto;

import org.devnexus.model.Talk;

import java.io.Serializable;
import java.util.List;

public class TalksDTO implements Serializable {

    List<Talk> talks;

    public List<Talk> getTalks() {
        return talks;
    }

    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }

}
