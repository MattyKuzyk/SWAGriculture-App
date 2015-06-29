package com.swagriculture.swagriculture_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.entity.mime.MultipartEntity;
import com.parse.entity.mime.content.StringBody;

import org.apache.http.HttpEntity;
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
 * Wrapper around Google's zxing library for general barcode scanning
 */
public class QRScanner extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main); // Apparently I need this??
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE","QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }
    private class HandleClick implements View.OnClickListener {


        public void onClick(View view){

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                System.out.println("QR RESULT: " + intent.getStringExtra("SCAN_RESULT"));
                new SendPostRequestTask().execute(intent.getStringExtra("SCAN_RESULT"));
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("QR Result: sadness");
            }
        }
    }


}
