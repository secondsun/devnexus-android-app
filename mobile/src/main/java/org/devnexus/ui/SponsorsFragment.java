package org.devnexus.ui;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mikepenz.fastadapter.AbstractAdapter;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.materialize.MaterializeBuilder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.devnexus.R;
import org.devnexus.model.Sponsor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SponsorsFragment extends Fragment {

    private static final String TAG = SponsorsFragment.class.getName();

    private ItemAdapter itemAdapter;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.sponsors)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_sponsors, container, false);

        ButterKnife.bind(this, view);

        FastAdapter<Sponsor> fastAdapter = new FastAdapter<>();
        fastAdapter.setHasStableIds(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<Sponsor>() {
            @Override
            public boolean onClick(View v, IAdapter<Sponsor> adapter, Sponsor item, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getLink()));
                startActivity(intent);
                return false;
            }
        });

        StickyHeaderAdapter stickyHeaderAdapter = new StickyHeaderAdapter();
        HeaderAdapter headerAdapter = new HeaderAdapter();
        itemAdapter = new ItemAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(
                stickyHeaderAdapter.wrap(
                        itemAdapter.wrap(
                                headerAdapter.wrap(
                                        fastAdapter
                                )
                        )
                )
        );

        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(
                stickyHeaderAdapter);
        mRecyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(stickyHeaderAdapter));

        stickyHeaderAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                decoration.invalidateHeaders();
            }
        });

        loadSponsorsFromServer();

        return view;
    }

    public void loadSponsorsFromServer() {
        new SponsorsTask().execute();
    }

    private class StickyHeaderAdapter extends AbstractAdapter
            implements StickyRecyclerHeadersAdapter {

        @Override
        public long getHeaderId(int position) {
            Sponsor sponsor = (Sponsor) getItem(position);
            return sponsor.getLevel().charAt(0);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_sponsor_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            Sponsor sponsor = (Sponsor) getItem(position);
            TextView textView = (TextView) holder.itemView;
            textView.setText(sponsor.getLevel());
        }

        @Override
        public int getOrder() {
            return -100;
        }

        @Override
        public int getAdapterItemCount() {
            return 0;
        }

        @Override
        public List<IItem> getAdapterItems() {
            return null;
        }

        @Override
        public IItem getAdapterItem(int position) {
            return null;
        }

        @Override
        public int getAdapterPosition(IItem item) {
            return -1;
        }

        @Override
        public int getAdapterPosition(long identifier) {
            return -1;
        }

        @Override
        public int getGlobalPosition(int position) {
            return -1;
        }

    }

    private class SponsorsTask extends AsyncTask<Void, Void, List<Sponsor>> {

        private Exception error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        @Override
        protected List<Sponsor> doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://devnexus.com/api/sponsors.json")
                    .build();

            List<Sponsor> sponsors = new ArrayList<>();

            try {
                Response responses = client.newCall(request).execute();
                String jsonData = responses.body().string();

                JsonElement jsonRootElement = new JsonParser().parse(jsonData);
                JsonArray sponsorsJsonArray = jsonRootElement.getAsJsonObject()
                        .getAsJsonArray("sponsors");

                for (int i = 0; i < sponsorsJsonArray.size(); i++) {
                    final JsonElement jsonElement = sponsorsJsonArray.get(i);
                    final Sponsor sponsor = new Gson().fromJson(jsonElement, Sponsor.class);
                    sponsors.add(sponsor);
                }

                Log.d("sponsordJsonArray", jsonData);

            } catch (IOException e) {
                error = e;
                Log.e(TAG, e.getMessage(), e);
            }

            return sponsors;

        }

        @Override
        protected void onPostExecute(List<Sponsor> sponsors) {
            super.onPostExecute(sponsors);

            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            if(error == null) {
                itemAdapter.set(sponsors);
            } else {
                Snackbar.make(
                        mRecyclerView,
                        R.string.error_processing_request,
                        Snackbar.LENGTH_LONG
                ).setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadSponsorsFromServer();
                    }
                }).show();
            }
        }

    }

}
