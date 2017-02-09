package org.ayo.lang.sample;

import android.app.Application;
import android.util.Log;

import org.ayo.Ayo;
import org.ayo.bug.BeforeNotify;
import org.ayo.bug.Bugsnag;
import org.ayo.bug.Error;
import org.ayo.file.Files;
import org.ayo.lang.Lang;
import org.ayo.log.XLog;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class App extends Application {

    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        App.app = this;

        Ayo.init(this, "lang-test", true, true);

        String s = Files.file.getContent("/data/data/org.ayo.lang.sample/cache/bugsnag-errors/1486629112142.json");
        XLog.json(s);


        // Initialize the Bugsnag client
        Bugsnag.init(this);

        // Execute some code before every bugsnag notification
        Bugsnag.beforeNotify(new BeforeNotify() {
            @Override
            public boolean run(Error error) {
                System.out.println(String.format("In beforeNotify - %s", error.getExceptionName()));
                return true;
            }
        });

        // Set the user information
        Bugsnag.setUser("123456", "james@example.com", "James Smith");

        //Bugsnag.setProjectPackages("com.bugsnag.android.example", "com.bugsnag.android.other");

        // Add some global metaData
        Bugsnag.addToTab("user", "age", 31);
        Bugsnag.addToTab("custom", "account", "something");


        Log.e("sign", Lang.app.getSign());
    }
}
