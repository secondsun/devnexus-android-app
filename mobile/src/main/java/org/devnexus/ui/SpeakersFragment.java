package org.devnexus.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import org.devnexus.R;
import org.devnexus.model.Speaker;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpeakersFragment extends Fragment {

    private static final String TAG = SpeakersFragment.class.getName();

    private ItemAdapter itemAdapter;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.speakers)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_speakers, container, false);

        ButterKnife.bind(this, view);

        FastAdapter<Speaker> fastAdapter = new FastAdapter<>();
        fastAdapter.setHasStableIds(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<Speaker>() {
            @Override
            public boolean onClick(View v, IAdapter<Speaker> adapter, Speaker item, int position) {
                Intent intent = new Intent(getContext(), SpeakerActivity.class);
                intent.putExtra(Speaker.class.getName(), item);
                startActivity(intent);
                return false;
            }
        });

        itemAdapter = new ItemAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(itemAdapter.wrap(fastAdapter));

        loadSpeakersFromServer();

        return view;
    }

    private void loadSpeakersFromServer() {
        new SpeakersTask().execute();
    }

    private class SpeakersTask extends AsyncTask<Void, Void, List<Speaker>> {

        private Exception error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        @Override
        protected List<Speaker> doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://devnexus.com/api/speakers.jsonaa")
                    .build();

            try {
                Response responses = client.newCall(request).execute();
                String jsonData = responses.body().string();

                Type listType = new TypeToken<ArrayList<Speaker>>() {
                }.getType();
                return new Gson().fromJson(jsonData, listType);
            } catch (IOException e) {
                error = e;
                Log.e(TAG, e.getMessage(), e);

                return new ArrayList<>();
            }

        }

        @Override
        protected void onPostExecute(List<Speaker> speakers) {
            super.onPostExecute(speakers);

            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            if (error == null) {
                Collections.sort(speakers, new Comparator<Speaker>() {
                    @Override
                    public int compare(Speaker speaker1, Speaker speaker2) {

                        return speaker1.getName().compareTo(speaker2.getName());
                    }
                });

                itemAdapter.set(speakers);
            } else {
                Snackbar.make(
                        mRecyclerView,
                        R.string.error_processing_request,
                        Snackbar.LENGTH_LONG
                ).setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadSpeakersFromServer();
                    }
                }).show();
            }
        }

    }


}
