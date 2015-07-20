package com.swagriculture.swagriculture_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by user on 21-Jul-15.
 */
public class TrapNameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.trap_name_activity);
    }
//    Intent data = new Intent();
//    data.putExtra("QRResult",intent.getStringExtra("SCAN_RESULT"));
//    setResult(Activity.RESULT_OK, data);
//    finish();
    public void okButtonCallback(View view){
        String trapname = ((EditText)findViewById(R.id.trapNameEditText)).getText().toString();
        if(!trapname.equals("")) {
            Intent data = new Intent();
            data.putExtra("TrapName", trapname);
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }
    public void cancelButtonCallback(View view){
        Intent data = new Intent();
        data.putExtra("TrapName","");
        setResult(Activity.RESULT_CANCELED,data);
        finish();
    }
}
