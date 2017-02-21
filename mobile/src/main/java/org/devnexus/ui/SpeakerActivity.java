package org.devnexus.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mikepenz.materialize.MaterializeBuilder;

import org.devnexus.R;
import org.devnexus.model.Speaker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpeakerActivity extends AppCompatActivity {

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.company)
    TextView company;

    @BindView(R.id.bio)
    TextView bio;

    @BindView(R.id.twitter)
    ImageView twitter;

    @BindView(R.id.google_plus)
    ImageView googlePlus;

    @BindView(R.id.linkedin)
    ImageView linkedIn;

    @BindView(R.id.lanyrd)
    ImageView lanyrd;

    @BindView(R.id.github)
    ImageView github;

    private Speaker speaker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        ButterKnife.bind(this);

        new MaterializeBuilder().withActivity(this).build();

        speaker = (Speaker) getIntent().getSerializableExtra(Speaker.class.getName());

        Glide.with(getApplicationContext())
                .load(getString(R.string.speaker_avatar_url, String.valueOf(speaker.getId())))
                .into(avatar);

        Glide.with(getApplicationContext())
                .load(getString(R.string.speaker_avatar_url, String.valueOf(speaker.getId())))
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(getApplicationContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        avatar.setImageDrawable(circularBitmapDrawable);
                    }
                });

        name.setText(speaker.getName());
        company.setText(speaker.getCompany());
        bio.setText(speaker.getBio());

        setVisibilitty(twitter, speaker.getTwitter());
        setVisibilitty(googlePlus, speaker.getGooglePlus());
        setVisibilitty(linkedIn, speaker.getLinkedIn());
        setVisibilitty(lanyrd, speaker.getLanyrd());
        setVisibilitty(github, speaker.getGithub());
    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.twitter)
    void twitter() {
        navigateTo("https://twitter.com/" + speaker.getTwitter());
    }

    @OnClick(R.id.google_plus)
    void googlePlus() {
        navigateTo("https://plus.google.com/" + speaker.getGooglePlus());
    }

    @OnClick(R.id.linkedin)
    void linkedIn() {
        navigateTo("https://www.linkedin.com/in/" + speaker.getLinkedIn());
    }

    @OnClick(R.id.lanyrd)
    void lanyrd() {
        navigateTo("http://lanyrd.com/profile/" + speaker.getLanyrd());
    }

    @OnClick(R.id.github)
    void github() {
        navigateTo("https://github.com/" + speaker.getGithub());
    }

    private void navigateTo(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Check if the string is null or blank
     *
     * @param s String to be tested
     * @return true for blank/null
     */
    private boolean isBlank(String s) {
        return s == null || "".equals(s);
    }

    /**
     * Hidde TextView with date is blank
     *
     * @param view View to be hidden
     * @param data String to be tested
     */
    private void setVisibilitty(View view, String data) {
        view.setVisibility((isBlank(data)) ? View.GONE : View.VISIBLE);
    }

}
