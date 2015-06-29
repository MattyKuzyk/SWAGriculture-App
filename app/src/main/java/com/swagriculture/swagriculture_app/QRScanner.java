package com.swagriculture.swagriculture_app;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by user on 29-Jun-15.
 * Wrapper around Google's zxing library for general barcode scanning
 */
public class QRScanner extends Activity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private String latitude;
    private String longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE","QR_CODE_MODE");
        startActivityForResult(intent, 0);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                System.out.println("QR RESULT: " + intent.getStringExtra("SCAN_RESULT"));
                if(mLastLocation != null){
                    try{
                        sendLocation(Double.toString(mLastLocation.getLongitude()),
                                     Double.toString(mLastLocation.getLatitude()),
                                     intent.getStringExtra("SCAN_RESULT"));
                    } catch(JSONException e){
                        System.out.println("Volley: send fail");
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("QR Result: sadness");
            }
        }
    }

    public void sendLocation(String longitude, String latitude, String trapId) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("trapId",trapId);
        data.put("longitude",longitude);
        data.put("latitude",latitude);
        data.put("name","Besterest Trap");
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

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastLocation != null) {
//            // Push logic goes here
//
//            try {
//                sendLocation(String.valueOf(mLastLocation.getLongitude()), String.valueOf(mLastLocation.getLatitude()));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Connection", "Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Connection", "Failed");
    }
}
