package com.swagriculture.swagriculture_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static int LOCATION_REQUEST_CODE = 1;
    private static int QR_SCAN_REQUEST_CODE = 2;
    private static int TRAP_NAME_REQUEST_CODE = 3;
    private static int POST_REQUEST_CODE = 4;

    private String latitude = "";
    private String longitude = "";
    private String trapId = "";
    private String trapName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void scanQR(View view){
        Intent intent = new Intent(this, QRScanner.class);
        startActivityForResult(intent, QR_SCAN_REQUEST_CODE);

    }
    public void getLocation(View view){
        Intent intent = new Intent(this, LocationService.class);
        startActivityForResult(intent, LOCATION_REQUEST_CODE);
    }
    public void sendPostRequest(View view){
        String name = ((EditText)findViewById(R.id.trapName)).getText().toString();
        String latitude = ((TextView)findViewById(R.id.latitudeData)).getText().toString();
        String longitude = ((TextView)findViewById(R.id.longitudeData)).getText().toString();
        String id = ((TextView)findViewById(R.id.trapIdData)).getText().toString();

        if(!(name.equals("") || latitude.equals("") || longitude.equals("") || id.equals(""))){
            // If all fields are non-blank
            Intent intent = new Intent(this, PostRequestActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            intent.putExtra("id",id);

            startActivity(intent);
        } else {

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == LOCATION_REQUEST_CODE) {
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");

            // Bring up screen for user to enter trap name
            Intent intent = new Intent(this, TrapNameActivity.class);
            startActivityForResult(intent, TRAP_NAME_REQUEST_CODE);
        } else if(requestCode == QR_SCAN_REQUEST_CODE){
            trapId = data.getStringExtra("QRResult");

            Intent intent = new Intent(this, LocationService.class);
            startActivityForResult(intent, LOCATION_REQUEST_CODE);
        } else if(requestCode == TRAP_NAME_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {
                trapName = data.getStringExtra("TrapName");

                saveTrapData();
            }
        }
    }
    public void saveTrapData(){
        if(!(trapName.equals("") || latitude.equals("") || longitude.equals("") || trapId.equals(""))){
            // If all fields are non-blank
            Intent intent = new Intent(this, PostRequestActivity.class);
            intent.putExtra("name",trapName);
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            intent.putExtra("id",trapId);

            startActivity(intent);

            trapName = "";
            latitude = "";
            longitude = "";
            trapId = "";
        }
    }
}
