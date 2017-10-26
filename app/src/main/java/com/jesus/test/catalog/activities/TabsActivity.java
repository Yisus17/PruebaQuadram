package com.jesus.test.catalog.activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jesus.test.catalog.MyApplication;
import com.jesus.test.catalog.R;
import com.jesus.test.catalog.fragments.Tab1Free;
import com.jesus.test.catalog.fragments.Tab2Pay;
import com.jesus.test.catalog.models.Feed;

public class TabsActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Feed feed, payFeed;

    public Feed getFeed() {
        return feed;
    }

    public Feed getPayFeed() {
        return payFeed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setPayFeed(Feed payFeed) {
        this.payFeed = payFeed;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        //Obteniendo la data del feed

        setFeed(((MyApplication) this.getApplication()).getFeed());
        setPayFeed(((MyApplication) this.getApplication()).getPayFeed());

        /****/

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1Free tab1Free = new Tab1Free();
                    return tab1Free;
                case 1:
                    Tab2Pay tab2Pay = new Tab2Pay();
                    return tab2Pay;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Free";
                case 1:
                    return "Paid";

            }
            return null;
        }
    }
}
