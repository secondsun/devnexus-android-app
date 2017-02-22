package org.devnexus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import org.devnexus.R;
import org.devnexus.dto.TalksDTO;
import org.devnexus.model.Talk;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TalksFragment extends Fragment {

    @BindView(R.id.talks)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_talks, container, false);

        ButterKnife.bind(this, view);

        TalksDTO dto = (TalksDTO) getArguments().getSerializable(TalksDTO.class.getName());
        List<Talk> talks = dto.getTalks();

        FastAdapter<Talk> fastAdapter = new FastAdapter<>();
        fastAdapter.setHasStableIds(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<Talk>() {
            @Override
            public boolean onClick(View v, IAdapter<Talk> adapter, Talk item, int position) {
                Intent intent = new Intent(getContext(), TalkActivity.class);
                intent.putExtra(Talk.class.getName(), item);
                startActivity(intent);
                return true;
            }
        });

        ItemAdapter<Talk> itemAdapter = new ItemAdapter<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                layoutManager.getOrientation()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(itemAdapter.wrap(fastAdapter));

        itemAdapter.set(talks);

        return view;
    }

}
