package org.ayo.lang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.ayo.Ayo;
import org.ayo.lang.Lang;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ayo.init(getApplication(), "dd", true, false);
        Log.e("sign", Lang.app.getSign());
    }
}
