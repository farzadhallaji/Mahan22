package com.comtech.ali.mahan2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
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
import com.comtech.ali.mahan2.Helper.ItemHalatiKeTypeYekNist;
import com.comtech.ali.mahan2.model.GlobalVar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ActivityShowNews extends AppCompatActivity {
    RequestQueue MyRequestQueue;
    String contentid="";
    String typeid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shownews);


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
                MyData.put("userid", GlobalVar.getUserID()); //TODO dis cmnt
                //MyData.put("userid", "1"); //Add the data you'd like to send to the server.
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

            TextView teriks = (TextView) findViewById(R.id.teriks);
            teriks.setText(jsonObject.getString("PTime"));

            TextView textView = (TextView) findViewById(R.id.textviewmiu);
            assert textView != null;
            textView.setText(Html.fromHtml(jsonObject.getString("Content")));
            //textView.setMovementMethod(new ScrollingMovementMethod());

            TextView descriptionnnn = (TextView) findViewById(R.id.tutle);
            assert descriptionnnn != null;
            descriptionnnn.setText(jsonObject.getString("Title"));

            ImageView imageView = (ImageView)findViewById(R.id.photo);
            new DownloadImageTask(imageView).execute(jsonObject.getString("PicURL"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
