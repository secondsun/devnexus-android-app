package org.devnexus.ui;

import android.content.Intent;
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

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import org.devnexus.R;
import org.devnexus.handler.SpeakerJsonHandler;
import org.devnexus.model.Speaker;
import org.devnexus.service.Callback;
import org.devnexus.service.RemoteDataAsyncTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeakersFragment extends Fragment {

    private static final String TAG = SpeakersFragment.class.getName();

    private ItemAdapter<Speaker> itemAdapter;

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
                return true;
            }
        });

        itemAdapter = new ItemAdapter<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(itemAdapter.wrap(fastAdapter));

        loadSpeakersFromServer();

        return view;
    }

    private void loadSpeakersFromServer() {
        new RemoteDataAsyncTask<Speaker>()
                .withUrl(getActivity().getString(R.string.speakers_url))
                .withHandler(new SpeakerJsonHandler())
                .withCallback(new Callback<Speaker>() {
                    @Override
                    public void onPreExecute() {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFinish() {
                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(List<Speaker> data) {
                        itemAdapter.set(data);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, e.getMessage(), e);

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
                })
                .execute();
    }

}
