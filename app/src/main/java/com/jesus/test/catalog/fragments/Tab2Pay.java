package com.jesus.test.catalog.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jesus.test.catalog.R;
import com.jesus.test.catalog.activities.TabsActivity;
import com.jesus.test.catalog.adapters.AppListAdapter;
import com.jesus.test.catalog.models.Feed;



/**
 * Created by jaas1 on 10/26/2017.
 */

public class Tab2Pay extends Fragment {

    private AppListAdapter appListAdapterTab2;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2pay, container, false);

        TabsActivity activity = (TabsActivity) getActivity();
        Feed feed = activity.getPayFeed();

        fillGrid(rootView);

        appListAdapterTab2.addList(feed.getEntry());

        return rootView;
    }


    private void fillGrid(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTab2);
        appListAdapterTab2 = new AppListAdapter(getContext());
        recyclerView.setAdapter(appListAdapterTab2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

}
