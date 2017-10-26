package com.jesus.test.catalog.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jesus.test.catalog.R;
import com.jesus.test.catalog.activities.TabsActivity;
import com.jesus.test.catalog.adapters.AppListAdapter;
import com.jesus.test.catalog.models.Feed;

/**
 * Created by jaas1 on 10/26/2017.
 */

public class Tab1Free extends Fragment{

    private AppListAdapter appListAdapterTab1;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1free, container, false);

        TabsActivity activity = (TabsActivity) getActivity();
        Feed feed = activity.getFeed();

        fillGrid(rootView);

        appListAdapterTab1.addList(feed.getEntry());

        return rootView;
    }

    private void fillGrid(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTab1);
        appListAdapterTab1 = new AppListAdapter(getContext());
        recyclerView.setAdapter(appListAdapterTab1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

}
