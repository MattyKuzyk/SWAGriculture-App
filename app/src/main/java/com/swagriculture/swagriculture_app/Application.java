package com.swagriculture.swagriculture_app;

/**
 * Created by Matthew on 6/28/2015.
 */
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class Application extends android.app.Application {

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Parse SDK.

        Parse.initialize(this, "eJMnNRD684vVSgW6aTjuwt6aOegfEf1C7xMuvDln", "QRBj9Kk21mYxG9czBV7K7uF7zfpCcucXSZKRlbOo");

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        // Specify an Activity to handle all pushes by default.
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

    }
}
