package com.jesus.test.catalog;

import android.app.Application;

import com.jesus.test.catalog.models.App;
import com.jesus.test.catalog.models.Feed;

/**
 * Created by jaas1 on 10/26/2017.
 */

public class MyApplication extends Application {
    Feed feed, payFeed;
    boolean done;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Feed getPayFeed() {
        return payFeed;
    }

    public void setPayFeed(Feed payFeed) {
        this.payFeed = payFeed;
    }

    public void actFeed(App myApp, Boolean mark) {
        for (App app : getFeed().getEntry()) {
            if (app.getId().getLabel().equals(myApp.getId().getLabel())) {
                app.setFav(mark);
                done = true;
            }
        }
        if (done == false){
            for (App app : getPayFeed().getEntry()) {
                if (app.getId().getLabel().equals(myApp.getId().getLabel())) {
                    app.setFav(mark);
                    done = true;
                }
            }
        }
        done = false;
    }


}
