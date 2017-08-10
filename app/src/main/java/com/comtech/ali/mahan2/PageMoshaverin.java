package com.comtech.ali.mahan2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.comtech.ali.mahan2.Helper.ItemServices;
import com.comtech.ali.mahan2.model.GlobalVar;
import com.comtech.ali.mahan2.model.Services;
import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PageMoshaverin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
  //  String userid="";
   // int soallllllll=0;

    View ftView;
    GridView listView;
    ItemServices adapter;
    List<Services> totalList=new ArrayList<>();
    int tempcount=0;
    private RequestQueue MyRequestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Button button = (Button) findViewById(R.id.showSpinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageMoshaverin.this, ListServicesByUser.class);
                startActivity(intent);
            }
        });
         Fabric.with(this, new Crashlytics());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView imageView = (ImageView) findViewById(R.id.menuButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(GravityCompat.END);

            }
        });

        if (isOnline()) {
            method2(GlobalVar.getUserID(),"getservices");
        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }


        listView = (GridView) findViewById(R.id.gridview);


        /*listView.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //getApplication()
                Log.i("zexcdfvgbhjnkml", totalList.get(position).getServiceType());
                Intent intent = new Intent(PageMoshaverin.this, ExplainMoshaver.class);
                intent.putExtra("ServiceID", totalList.get(position).getServiceID());
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);  //
                //
            }

        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Log.i("zexcdfvgbhjnkml", "asas :"+position+"");
                    Log.i("zexcdfvgbhjnkml", "asas :"+view.getId());

                Log.i("zexcdfvgbhjnkml", totalList.get(position).getServiceType());


            }
        });


        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);


    }
    void method2(final String userid ,final String fnname) {

     ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
     progressbarsandaha.setVisibility(View.VISIBLE);
        final TextView textView=(TextView)findViewById(R.id.myTextProgress);
        textView.setVisibility(View.VISIBLE);


        MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://iusnews.ir/webservicesmahan/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("qwertyuioaladfffgree", response);

                List<Services> templist=new ArrayList<>();
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
                        Services temp = new Services(s.get("Service").toString(),s.get("ServiceType").toString(),s.get("ServiceID").toString(),s.getString("IconURL").toString());
                        templist.add(temp);
                    }
                    totalList.addAll(templist);
                    if(tempcount==0){
                        adapter =new ItemServices(PageMoshaverin.this,totalList);  //deghat dar sorat taghir name class
                        listView.setAdapter(adapter);
                        tempcount++;

                    }
                    adapter.notifyDataSetChanged();
                    // listView.removeFooterView(ftView);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

               ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
                progressbarsandaha.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                // Log.i("asasasasasasa",adviseridm+"/"+GlobalVar.getDeviceID());
                MyData.put("fnname", "getservices");
                MyData.put("userid", GlobalVar.getUserID());

                Log.i("qwertyuioaladfffgree",MyData.toString());//
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
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

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            Intent intent =new Intent(this , pageExit.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_alaghe) {
            Intent intent =new Intent(this , PageAlaghe.class);
            finish();
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
      /*  Intent intent = new Intent(PageMoshaverin.this,PageLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/
        finish();
    }


}