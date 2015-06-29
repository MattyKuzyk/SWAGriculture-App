package com.swagriculture.swagriculture_app;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29-Jun-15.
 */
public class SendPostRequestTask extends AsyncTask<String, Void, Integer> {

    @Override
    public Integer doInBackground(String... params) {
        // TODO: refactor - put in separate class. Figure out how to do without deprecated classes
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://swagriculture.parseapp.com/trap");
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
            nameValuePair.add(new BasicNameValuePair("trapId", "0028DD0F"));
            nameValuePair.add(new BasicNameValuePair("name", "Trap 1"));
            nameValuePair.add(new BasicNameValuePair("longitude", "-80.5343984"));
            nameValuePair.add(new BasicNameValuePair("latitude", "43.4778646"));

            post.setEntity(new UrlEncodedFormEntity(nameValuePair));

            HttpResponse response = client.execute(post);
            // write response to log
            Log.d("Http Post Response:", response.toString());

        } catch (Exception e) {

            e.printStackTrace();
        }
        return 0;
    }
}
