package org.devnexus.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.devnexus.R;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Speaker extends AbstractItem<Speaker, Speaker.ViewHolder> implements Serializable {

    private int id;
    private int version;
    private String firstName;
    private String lastName;
    private String company;
    private String bio;
    @SerializedName("twitterId")
    private String twitter;
    @SerializedName("googlePlusId")
    private String googlePlus;
    @SerializedName("linkedInId")
    private String linkedIn;
    @SerializedName("lanyrdId")
    private String lanyrd;
    @SerializedName("githubId")
    private String github;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getLanyrd() {
        return lanyrd;
    }

    public void setLanyrd(String lanyrd) {
        this.lanyrd = lanyrd;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    @Override
    public int getType() {
        return id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_speaker_item;
    }

    @Override
    public void bindView(final Speaker.ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        final Context context = holder.itemView.getContext();

        holder.name.setText(getName());

        Glide.with(context)
                .load(context.getString(R.string.speaker_avatar_url, String.valueOf(id)))
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(holder.avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.avatar.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    @Override
    public void unbindView(Speaker.ViewHolder holder) {
        super.unbindView(holder);

        holder.name.setText("");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView avatar;

        @BindView(R.id.name)
        TextView name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
