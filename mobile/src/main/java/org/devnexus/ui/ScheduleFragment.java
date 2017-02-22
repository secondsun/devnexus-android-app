package org.devnexus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.devnexus.R;
import org.devnexus.dto.TalksDTO;
import org.devnexus.handler.TalkJsonHandler;
import org.devnexus.model.Talk;
import org.devnexus.service.Callback;
import org.devnexus.service.RawDataService;
import org.devnexus.service.RemoteDataAsyncTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment {

    private static final String TAG = ScheduleFragment.class.getName();

    private Map<String, List<Talk>> talksByDay;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_schedule, container, false);

        ButterKnife.bind(this, view);

        loadTalksFromServer();

        tabs.setupWithViewPager(pager);

        return view;
    }

    private void loadTalksFromServer() {
        new RemoteDataAsyncTask<Talk>()
                .withUrl(getActivity().getString(R.string.talks_json))
                .withHandler(new TalkJsonHandler())
                .withCallback(new Callback<Talk>() {
                    @Override
                    public void onPreExecute() {
                        tabs.setVisibility(View.GONE);
                        pager.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        tabs.setVisibility(View.VISIBLE);
                        pager.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(List<Talk> data) {
                        talksByDay = splitTalksInDays(data);
                        pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, e.getMessage(), e);

                        Snackbar.make(
                                tabs,
                                R.string.error_processing_request,
                                Snackbar.LENGTH_LONG
                        ).setAction(R.string.try_again, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadTalksFromServer();
                            }
                        }).show();
                    }
                })
                .execute();
    }

    private Map<String, List<Talk>> splitTalksInDays(List<Talk> talks) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Map<String, List<Talk>> talksByDay = new HashMap<>();

        for (Talk talk : talks) {
            String date = simpleDateFormat.format(talk.getStarts());

            if (!talksByDay.containsKey(date)) {
                talksByDay.put(date, new ArrayList<Talk>());
            }

            talksByDay.get(date).add(talk);

        }

        return talksByDay;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String date = (String) (talksByDay.keySet().toArray())[position];
            return date;
        }

        @Override
        public Fragment getItem(int position) {
            List<Talk> talks = talksByDay.get((talksByDay.keySet().toArray())[position]);

            TalksDTO dto = new TalksDTO();
            dto.setTalks(talks);

            Bundle args = new Bundle();
            args.putSerializable(TalksDTO.class.getName(), dto);

            TalksFragment fragment = new TalksFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return talksByDay.size();
        }

    }


}
