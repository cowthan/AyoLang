package org.ayo.lang.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.ayo.bug.BreadcrumbType;
import org.ayo.bug.Bugsnag;
import org.ayo.bug.Callback;
import org.ayo.bug.MetaData;
import org.ayo.bug.Report;
import org.ayo.bug.Severity;
import org.ayo.lang.sample.other.Other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class BugExampleActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Bugsnag.leaveBreadcrumb("onCreate", BreadcrumbType.NAVIGATION, new HashMap<String, String>());

        new Thread(new Runnable() {
            public void run() {
                try {
                    sleepSoundly();
                } catch (InterruptedException e) {

                }
            }

            private void sleepSoundly() throws InterruptedException {
                Thread.sleep(100000);
            }
        }).start();
    }

    public void sendError(View view) {
        actuallySendError();
    }

    private void actuallySendError() {
        Bugsnag.notify(new RuntimeException("Non-fatal error"), Severity.ERROR);
        Toast.makeText(this, "Sent error", LENGTH_SHORT).show();
    }

    public void sendWarning(View view) {
        actuallySendWarning();
    }

    private void actuallySendWarning() {
        Bugsnag.notify(new RuntimeException("Non-fatal warning"), Severity.WARNING);
        Toast.makeText(this, "Sent warning", LENGTH_SHORT).show();
    }

    public void sendInfo(View view) {
        Bugsnag.notify(new RuntimeException("Non-fatal info"), Severity.INFO);
        Toast.makeText(this, "Sent info", LENGTH_SHORT).show();
    }

    public void sendErrorWithMetaData(View view) {
        Map<String, String> nested = new HashMap<String, String>();
        nested.put("normalkey", "normalvalue");
        nested.put("password", "s3cr3t");

        Collection list = new ArrayList();
        list.add(nested);

        final MetaData metaData = new MetaData();
        metaData.addToTab("user", "payingCustomer", true);
        metaData.addToTab("user", "password", "p4ssw0rd");
        metaData.addToTab("user", "credentials", nested);
        metaData.addToTab("user", "more", list);

        //Bugsnag.notify(new RuntimeException("Non-fatal error with metaData"), Severity.ERROR, metaData);
        Bugsnag.notify(new RuntimeException("Non-fatal error with metaData"), new Callback() {
            @Override
            public void beforeNotify(Report report) {
                report.getError().setSeverity(Severity.ERROR);
                report.getError().setMetaData(metaData);
            }
        });
        Toast.makeText(this, "Sent error with metaData", LENGTH_SHORT).show();
    }

    public void crash(View view) {
        Other other = new Other();
        other.meow();
    }
}
