package org.devnexus.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.materialize.MaterializeBuilder;

import org.devnexus.R;
import org.devnexus.model.Speaker;
import org.devnexus.model.Talk;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TalkActivity extends AppCompatActivity {

    private static final SimpleDateFormat TIME = new SimpleDateFormat("E M/dd hh:mm a", Locale.US);

    private Talk talk;

    @BindView(R.id.color)
    View color;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.room)
    TextView room;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.track_icon)
    ImageView trackIcon;

    @BindView(R.id.track)
    TextView track;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.speakers)
    RecyclerView speakers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        ButterKnife.bind(this);

        new MaterializeBuilder().withActivity(this).build();

        talk = (Talk) getIntent().getSerializableExtra(Talk.class.getName());

        // -- Speakers

        FastAdapter<Speaker> fastAdapter = new FastAdapter<>();
        fastAdapter.setHasStableIds(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<Speaker>() {
            @Override
            public boolean onClick(View v, IAdapter<Speaker> adapter, Speaker item, int position) {
                Intent intent = new Intent(getApplicationContext(), SpeakerActivity.class);
                intent.putExtra(Speaker.class.getName(), item);
                startActivity(intent);
                return true;
            }
        });

        ItemAdapter<Speaker> itemAdapter = new ItemAdapter<>();

        speakers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        speakers.setItemAnimator(new DefaultItemAnimator());
        speakers.setAdapter(itemAdapter.wrap(fastAdapter));

        //  -- Set values

        // Uncomment if you wanna see the title in the toolbar
        // toolbarTitle.setText(talk.getTitle());

        color.setBackgroundColor(Color.parseColor(talk.getRoom().getColor()));
        title.setText(talk.getTitle());
        room.setText(talk.getRoom().getName());
        time.setText(TIME.format(talk.getStarts()));
        if(talk.getPresentation() != null) {
            track.setText(talk.getPresentation().getTrack().getName());
            description.setText(talk.getPresentation().getDescription());
            itemAdapter.set(talk.getPresentation().getSpeakers());
        } else {
            trackIcon.setVisibility(View.GONE);
            track.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

}
