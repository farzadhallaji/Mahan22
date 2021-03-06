package com.comtech.ali.mahan2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.comtech.ali.mahan2.internet.HttpManager;
import com.comtech.ali.mahan2.internet.RequestPackage;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    String imei, wifimac, uniqueid, androidid, simno, operator, brand, model, android_sdk, android_version, height, width, device_size, version, os = "";
    String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        EditText editText = (EditText)findViewById(R.id.EditSendNum);
        //Button sendnum=(Button)findViewById(R.id.ButtonSend);

        Fabric.with(this, new Crashlytics());



    initial();

    if (isOnline()) {
        //Toast.makeText(getApplicationContext(), "jeddi", Toast.LENGTH_LONG).show();
        source =  imei + "/" + wifimac + "/" + uniqueid + "/" + androidid + "/" + simno + "/" + operator + "/" + brand + "/" + model
                + "/" + android_sdk + "/" + android_version + "/" + height + "/" + width + "/" + device_size + "/" + version + "/" + os;

            /*Toast.makeText(getApplicationContext(), source, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), source, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), source, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), source, Toast.LENGTH_LONG).show();
             */

        requestData();

    } else {
        Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
    }
    }


  @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint("HardwareIds")
    private void initial() {

        device_size = "";
        version = "1.0";
        os = "android";

        try {
            @SuppressLint("ServiceCast")
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELECOM_SERVICE);
            imei = mngr.getDeviceId();                         /**/
            operator = mngr.getNetworkOperatorName();          /**/
            simno = mngr.getLine1Number();                     /**/
        } catch (Exception e) {
            Log.i("errorMainActivity", e.toString());
        }

        try {
            WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            wifimac = info.getMacAddress();
        } catch (Exception e) {
            Log.i("errorMainActivity", e.toString());
        }

        /*try {

            androidid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            uniqueid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.NAME);
        } catch (Exception e) {
            Log.i("errorMainActivity", e.toString());
        }
*/
        try {

            androidid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            uniqueid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.NAME);
        } catch (Exception e) {
            Log.i("err", e.toString());
        }

        try {

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = String.valueOf(displayMetrics.heightPixels);
            width = String.valueOf(displayMetrics.widthPixels);

            android_version = Build.VERSION.RELEASE;
            android_sdk = String.valueOf(Build.VERSION.SDK_INT);

            model = android.os.Build.MODEL;
            brand = Build.MANUFACTURER;

        }catch (Exception e){
            Log.i("err", e.toString());
        }

    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    private void requestData() {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");

        p.setUri("http://iusnews.ir/webservicesmahan/");
        p.setParam("fnname","check");
        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

    }
    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData2(params[0], source);
            return content;
        }
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
           // progressBar.setVisibility(View.GONE);
        //    GlobalVar.setDeviceID(result);

           Intent intent = new Intent(MainActivity.this, PageLogin.class);
         //   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
         //   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

        }


    }

}
