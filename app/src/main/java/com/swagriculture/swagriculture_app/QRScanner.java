package com.swagriculture.swagriculture_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by user on 29-Jun-15.
 * Wrapper around Google's zxing library for general barcode scanning
 */
public class QRScanner extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE","QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                System.out.println("QR RESULT: " + intent.getStringExtra("SCAN_RESULT"));

                Intent data = new Intent();
                data.putExtra("QRResult",intent.getStringExtra("SCAN_RESULT"));
                setResult(Activity.RESULT_OK, data);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("QR Result: sadness");
                Intent data = new Intent();
                data.putExtra("QRResult","");
                setResult(Activity.RESULT_CANCELED, data);
                finish();
            }
        }

    }
}
