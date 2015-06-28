package com.swagriculture.swagriculture_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
            //TextView tvStatus=(TextView)findViewById(R.id.tvStatus);
            TextView tvResult=(TextView)findViewById(R.id.QRResult);
            if (resultCode == RESULT_OK) {
                //tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
                tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
                //System.out.println(intent.getStringExtra("SCAN_RESULT"));
            } else if (resultCode == RESULT_CANCELED) {
                //tvStatus.setText("Press a button to start a scan.");
                tvResult.setText("Scan cancelled.");
            }
        }
    }
}
