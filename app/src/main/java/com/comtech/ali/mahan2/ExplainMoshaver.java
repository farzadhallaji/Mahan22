package com.comtech.ali.mahan2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comtech.ali.mahan2.Helper.ItemHalatiKeTypeYekAst;
import com.comtech.ali.mahan2.model.GlobalVar;
import com.comtech.ali.mahan2.model.HalatiKeTypeYekAst;
import com.crashlytics.android.Crashlytics;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExplainMoshaver extends Activity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private static AsyncHttpClient client = new AsyncHttpClient();
    RequestQueue MyRequestQueue;

    ItemHalatiKeTypeYekAst adapter;
    List<HalatiKeTypeYekAst> totalList=new ArrayList<>();
    int tempcount=0;


    String serviceidi="84";
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_movie);
        Fabric.with(this, new Crashlytics());

        gridView=(GridView)findViewById(R.id.gridview);

        /*   if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                placeid= "";
            } else {
                placeid= extras.getString("placeid");
            }
        } else {
            placeid= (String) savedInstanceState.getSerializable("placeid");
        }*/

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.menuButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                serviceidi= "0";
            } else {
                serviceidi= extras.getString("ServiceID");
            }
        } else {
            serviceidi= (String) savedInstanceState.getSerializable("ServiceID");
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
        final TextView textView=(TextView)findViewById(R.id.myTextProgress);
        textView.setVisibility(View.VISIBLE);


        MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://iusnews.ir/webservicesmahan/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Explainservice",response);
                updatelistview(response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> MyData = new HashMap<String, String>();

                MyData.put("serviceid", serviceidi); //Add the data you'd like to send to the server.
                MyData.put("fnname", "getservicescontent"); //Add the data you'd like to send to the server.
                MyData.put("userid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                MyData.put("start", "0");       //Add the data you'd like to send to the server.

                Log.i("ExplainMoshaver", MyData.toString());
                return MyData;
            }

            @Override
            protected void onFinish() {
                ProgressBar progressbarsandaha = (ProgressBar) findViewById(R.id.myProgress);
                progressbarsandaha.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }
    private void updatelistview(String response) {
        /*  try {
            JSONObject  jsonObject= new JSONObject(response);
            List<Services> templist=new ArrayList<>();
            List<String> strings=new ArrayList<>();
           JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("Tag").toString());
            Log.i("responseresponse",response);
            String tuzihat ="";
            try {
                for(int i= 0 ; i<jsonArray.length() ; i++){
                    tuzihat+=jsonArray.get(i);
                    if(i!=jsonArray.length()-1)
                        tuzihat+="  , ";
                    Log.i("asasfdghnghjyujyuj",tuzihat);
                }
            }catch (Exception ignored){}
            //JSONArray Licenses = new JSONArray(jsonObject.getJSONArray("License").toString());
            //   License = jsonObject.getString("License");
           // Log.i("Lieseesene",License);
            String ID = jsonObject.getString("ID");
            String ContentType = jsonObject.getString("ContentType");
            String Lead = jsonObject.get("Lead").toString();
            String Title = jsonObject.get("Title").toString();
            String PreTitle = jsonObject.get("PreTitle").toString();
            String PTime = jsonObject.getString("PTime");
            String Sort = jsonObject.getString("Sort");
            String PicURL = jsonObject.get("PicURL").toString();
            // String CostPerMin = jsonObject.get("AdviserName").toString();
            //IsFavorite= jsonObject.getString("IsFavourite").equals("1");
           // Mainplace= jsonObject.getString("mainplace");
            //Log.i("Explanfkdfdkfdfdfdfdfd",IsFavourite);
          *//*  if(IsFavorite){
                alagestarmoshaver.setImageResource(R.drawable.alage1);
            }else{
                alagestarmoshaver.setImageResource(R.drawable.alage0);
            }
            comments=jsonObject.getJSONArray("Comment").toString();
             JSONArray tags =new JSONArray(jsonObject.getString("Tag"));
                for(int ii=0 ; ii< tags.length() ; ii++){
                Tagggggggs+=tags.getString(ii)+"\n";
            }*//*
//            taxassose_moshaver_textview.setText(tuzihat);
*//*

            description.setText(Title);
            new DownloadImageTask(imagemovie).execute(PicURL);
*//*

            HalatiKeTypeYekAst moshaver = new HalatiKeTypeYekAst(ID, ContentType, Lead, Title, PreTitle, PTime, Sort, PicURL);
            moshaver.setContentType(ContentType);
            moshaver.setID(ID);
            moshaver.setLead(Lead);
            moshaver.setPicURL(PicURL);
            moshaver.setPTime(PTime);
            moshaver.setPreTitle(PreTitle);
            moshaver.setSort(Sort);
            moshaver.setTitle(Title);
            adapter=new ItemHalatiKeTypeYekAst(ExplainMoshaver.this,totalList);
            gridView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Log.i("qwertyuioaladfffgree", response);

        List<HalatiKeTypeYekAst> templist=new ArrayList<>();
        Log.i("saasasa",response) ;

        try {

            JSONArray jsonArray = new JSONArray(response);

            for(int i= 0 ; i<jsonArray.length() ; i++){
                JSONObject s = (JSONObject) jsonArray.get(i);
                // List<String>tag=new ArrayList<>();
                //JSONArray tags = (JSONArray) s.get("Tag");
                //Toast.makeText(getApplicationContext(),String.valueOf(tags), Toast.LENGTH_LONG).show();
                //for(int j= 0 ;j < tags.length() ; j++){

                //  tag.add(tags.get(j).toString());
                //Toast.makeText(getApplicationContext(),tags.get(j).toString(), Toast.LENGTH_LONG).show();

//                        }
                HalatiKeTypeYekAst temp = new HalatiKeTypeYekAst(s.get("ID").toString(),s.get("ContentType").toString(),s.get("Lead").toString(),s.getString("Title").toString(),
                        s.getString("PreTitle").toString(),s.getString("PTime").toString(),s.getString("PicURL").toString(),s.getString("Sort").toString());
                templist.add(temp);
            }
            totalList.addAll(templist);
            if(tempcount==0){
                adapter =new ItemHalatiKeTypeYekAst(ExplainMoshaver.this,totalList);  //deghat dar sorat taghir name class
                gridView.setAdapter(adapter);
                tempcount++;

            }
            adapter.notifyDataSetChanged();
            // listView.removeFooterView(ftView);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    /*    ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
        progressbarsandaha.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

*/








    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_alaghe) {
            Intent intent =new Intent(ExplainMoshaver.this , PageAlaghe.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent =new Intent(this , pageExit.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

   /* private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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
                //Log.e("Error", e.getMessage());
                //e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            result.recycle();
        }
    }*/

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.imagemovie));
        System.gc();
    }
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        //super.onBackPressed();
        Intent intent =new Intent(ExplainMoshaver.this , PageMoshaverin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
