package com.comtech.ali.mahan2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityShowMovie extends AppCompatActivity {


    RequestQueue MyRequestQueue;
    String contentid="";
    String typeid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                contentid= "";
                typeid= "";
            } else {

                typeid= extras.getString("typeid");
                contentid= extras.getString("contentid");

                Log.i("sdbfgbfgbfgb",typeid +"as"+ contentid);


            }
        } else {
            typeid= (String) savedInstanceState.getSerializable("typeid");
            contentid= (String) savedInstanceState.getSerializable("contentid");
        }


        if(isOnline()){
            postgetData();

            } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
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

    void postgetData(){

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
        progressbarsandaha.setVisibility(View.VISIBLE);


        MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://iusnews.ir/webservicesmahan/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("ActivityShowMoviy",response);
                updatelistview(response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("contentid", contentid); //Add the data you'd like to send to the server.
                MyData.put("typeid", typeid); //Add the data you'd like to send to the server.
                MyData.put("fnname", "getcontent"); //Add the data you'd like to send to the server.
                //MyData.put("userid", GlobalVar.getUserID()); //TODO dis cmnt
                MyData.put("userid", "1"); //Add the data you'd like to send to the server.
                MyData.put("typeid", typeid);       //Add the data you'd like to send to the server.

                Log.i("ActivityShowMoviy", MyData.toString());
                return MyData;
            }

            @Override
            protected void onFinish() {
                ProgressBar progressbarsandaha = (ProgressBar) findViewById(R.id.myProgress);
                progressbarsandaha.setVisibility(View.INVISIBLE);
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview(String response) {


        try {

            JSONObject jsonObject = new JSONObject(response);

            TextView tarix = (TextView) findViewById(R.id.tarix);
            assert tarix != null;
            tarix.setText(jsonObject.getString("PTime"));
            TextView descriptionnnn = (TextView) findViewById(R.id.descriptionnnn);
            assert descriptionnnn != null;
            descriptionnnn.setText(jsonObject.getString("Title"));

            String vidAddress =new JSONObject(new JSONArray(jsonObject.getString("Media")).get(0).toString()).getString("URL");
            Uri vidUri = Uri.parse(vidAddress);

            final VideoView videoView = (VideoView)findViewById(R.id.movieninahsi);
            assert videoView != null;
            videoView.setVideoURI(vidUri);

            final ImageView imageView = (ImageView)findViewById(R.id.playimg);
            assert imageView != null;
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setVisibility(View.GONE);
                    videoView.start();
                }
            });

            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.pause();
                    imageView.setVisibility(View.VISIBLE);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
