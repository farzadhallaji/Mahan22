package com.comtech.ali.mahan2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.comtech.ali.mahan2.model.GlobalVar;
import com.crashlytics.android.Crashlytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali on 7/20/2017.
 */
public class PageLogin extends AppCompatActivity {
    //implements NavigationView.OnNavigationItemSelectedListener{

    private static AsyncHttpClient client = new AsyncHttpClient();
    TextView textView;
    Button button;
    EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Fabric.with(this, new Crashlytics());

        textView = (TextView)findViewById(R.id.text_show);
        button = (Button) findViewById(R.id.ButtonSend);
        editText = (EditText) findViewById(R.id.EditSendNum);

        if(isOnline()){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "لطفا فیلد مربوطه را پر کنید", Toast.LENGTH_LONG).show();
                    }else if(editText.getText().toString().length()<11){
                        Toast.makeText(getApplicationContext(), "شماره تلفن وارد شده باید یازده رقمی باشد!", Toast.LENGTH_LONG).show();

                    }

                    else {
                        requestDataa();
                    }
                }
            });
            //requestDatsssa();
            //Log.i("tutal",aLagemandis.size()+ " ");
            //requestData();

        }else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }
      /*  button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button.setPressed(true);
                return true;
            }
        });*/
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


    private void requestDataa() {

         //  ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        //progressbarsandaha.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();

        params.put("phone",editText.getText().toString()); //Add the data you'd like to send to the server.
        params.put("fnname","mahanlogin");
        client.post("http://iusnews.ir/webservicesmahan/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    Log.i("'oliknhbvc", jsonObject.toString());
                    String Message = jsonObject.getString("msg");
                    String s = jsonObject.getString("state");

                    //    ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                    //  progressbarsandaha.setVisibility(View.INVISIBLE);

                    updategraf(Message, s, editText.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("rererrebfdfvv","1");
            }
        });
    }
    private void updategraf(String message, String s, final String phone) {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

        if(s.equals("1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(message);

            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                    changeui(phone);

                }
            });
        }else if(s.equals("-1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(message);
            TextView te =(TextView)dialogView.findViewById(R.id.aT);
            te.setText("خطا");
            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                }
            });



        }

    }

    private void changeui(final String phone) {

        editText.setText("");
        textView.setText("لطفا کد تائید را وارد نمائید:");
        // ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        //  progressbarsandaha.setVisibility(View.GONE);
        editText.setHint("کد تایید");
        button.setText("ارسال کد");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDataaa(phone);
            }
        });

    }

    private void requestDataaa(String phone) {
     //   ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
      //  progressbarsandaha.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();

        params.put("phone", phone);           //Add the data you'd like to send to the server.
        params.put("fnname","checksmscode");
        params.put("code", editText.getText().toString());   //Add the data you'd like to send to the server.

        Log.i("asdngh", params.toString());

        client.post("http://iusnews.ir/webservicesmahan/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    Log.i("gfgfgtr", jsonObject.toString());

                    // ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                    // progressbarsandaha.setVisibility(View.INVISIBLE);

                    try {

                        String Message = jsonObject.getString("msg");
                        String s = jsonObject.getString("state");
                        String Uid = jsonObject.getString("userid");

                        GlobalVar.setUserID(Uid);

                        if (s.equals("-1")) {
                            Uid = "";
                            updategraffff(Message, "-1", "");
                        } else {
                            try {
                                String State = jsonObject.getString("state");
                                String UID = jsonObject.getString("userid");
                                String msg = jsonObject.getString("msg");

                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UID", UID).apply();
                                GlobalVar.setUserID(UID);
                                // Log.i("asdfghhgbv", "sefgtbrvfvdfvdfvlgklkgfgb1");

                                Intent intent = new Intent(PageLogin.this, PageMoshaverin.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);


                            } catch (Exception ignored) {
                            }
                        }

                    } catch (Exception e) {
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("vgffgffgrew", "1");
                //   ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                // progressbarsandaha.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void updategraffff(String message, String s, final String phone) {

        LayoutInflater inflater = this.getLayoutInflater();




       //View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

    /*  if(s.equals("1")){

          final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

          dialogBuilder.setView(dialogView);

          TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
          textView.setText(message);

          Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            *//*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*//*
          final AlertDialog alertDialog = dialogBuilder.create();
          alertDialog.show();

          button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  alertDialog.cancel();}
          });*/
         if(s.equals("-1")){

           /* final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(message);
            TextView te =(TextView)dialogView.findViewById(R.id.aT);
            te.setText("خطا");
            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            *//*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*//*
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                }
            });*/
          Toast.makeText(getApplicationContext(), "کد اشتباه است! لطفا مجددا وارد نمائید", Toast.LENGTH_LONG).show();


        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }*/
      /*  Intent intent = new Intent(PageMoshaverin.this,PageLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/
        finish();
    }


}