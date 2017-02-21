package org.devnexus.model;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.squareup.picasso.Picasso;

import org.devnexus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Sponsor extends AbstractItem<Sponsor, Sponsor.ViewHolder> {

    private int id;
    private String name;
    private String link;
    @SerializedName("sponsorLevel")
    private String level;
    private String logo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public int getType() {
        return id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_sponsor_item;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        Glide.with(holder.itemView.getContext())
                .load("https://devnexus.com/api/sponsors/" + id + ".jpg")
                .into(holder.logo);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.logo)
        ImageView logo;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
