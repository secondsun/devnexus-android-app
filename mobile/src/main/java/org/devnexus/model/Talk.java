package org.devnexus.model;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.devnexus.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Talk extends AbstractItem<Talk, Talk.ViewHolder> implements Serializable {

    private static final SimpleDateFormat HOUR = new SimpleDateFormat("hh:mm a", Locale.US);

    private int id;
    private int version;
    private String title;
    @SerializedName("scheduleItemType")
    private String category;
    @SerializedName("fromTime")
    private Date starts;
    @SerializedName("toTime")
    private Date ends;

    private Presentation presentation;
    private Room room;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStarts() {
        return starts;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int getType() {
        return id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_talk_item;
    }

    @Override
    public void bindView(final Talk.ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        holder.color.setBackgroundColor(Color.parseColor(room.getColor()));
        holder.title.setText(title);
        holder.room.setText(room.getName());
        holder.time.setText(HOUR.format(starts) + " - " + HOUR.format(ends));
        if (presentation != null) {
            String speakers = "";
            for (int i = 0; i < presentation.getSpeakers().size(); i++) {
                Speaker speaker = presentation.getSpeakers().get(i);
                if (i > 0) {
                    speakers += ", ";
                }
                speakers += speaker.getName();
            }
            holder.speaker.setText(speakers);
            holder.track.setText(presentation.getTrack().getName());
        } else {
            holder.speakerIcon.setVisibility(View.GONE);
            holder.speaker.setVisibility(View.GONE);
            holder.trackIcon.setVisibility(View.GONE);
            holder.track.setVisibility(View.GONE);
        }

    }

    @Override
    public void unbindView(Talk.ViewHolder holder) {
        super.unbindView(holder);

        holder.title.setText("");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color)
        View color;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.speaker_icon)
        ImageView speakerIcon;

        @BindView(R.id.speaker)
        TextView speaker;

        @BindView(R.id.room)
        TextView room;

        @BindView(R.id.track_icon)
        ImageView trackIcon;

        @BindView(R.id.track)
        TextView track;

        @BindView(R.id.time)
        TextView time;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
