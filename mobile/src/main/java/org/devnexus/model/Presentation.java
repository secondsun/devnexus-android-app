package org.devnexus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Presentation implements Serializable {

    private int id;
    private int version;
    private String title;
    private String description;
    @SerializedName("presentationType")
    private String type;
    @SerializedName("skillLevel")
    private String level;
    private String audioLink;
    private String presentationLink;
    private Track track;
    private List<Speaker> speakers;
    private List<Tag> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public String getPresentationLink() {
        return presentationLink;
    }

    public void setPresentationLink(String presentationLink) {
        this.presentationLink = presentationLink;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
