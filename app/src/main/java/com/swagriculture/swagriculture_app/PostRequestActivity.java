package com.swagriculture.swagriculture_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 04-Jul-15.
 */
public class PostRequestActivity extends Activity {
    private String name;
    private String latitude;
    private String longitude;
    private String id;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            latitude = extras.getString("latitude");
            longitude = extras.getString("longitude");
            id = extras.getString("id");
            try {
                sendPostRequest(name, latitude, longitude, id);
            } catch (JSONException e) {
                System.out.println("Volley: send fail");
                e.printStackTrace();
            } finally {
                finish();
            }
        }
    }
    private void sendPostRequest(String name, String latitude, String longitude, String id) throws JSONException{
        JSONObject data = new JSONObject();
        data.put("trapId",id);
        data.put("longitude",longitude);
        data.put("latitude",latitude);
        data.put("name",name);
        String url = "http://swagriculture.parseapp.com/trap";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, data,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley", "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });
        queue.add(jsonObjReq);
    }
}
