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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comtech.ali.mahan2.Helper.ItemHalatiKeTypeYekNist;
import com.comtech.ali.mahan2.model.GlobalVar;
import com.comtech.ali.mahan2.model.HalatiKeYekNist;
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

public class serch extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    EditText editText;
    //String userid="";
    //int soallllllll=0;
    View ftView;
    ListView listView;
    ItemHalatiKeTypeYekNist adapter;
    List<HalatiKeYekNist> totalList=new ArrayList<>();
    int tempcount=0;
    private RequestQueue MyRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch);

        Button send = (Button)findViewById(R.id.sendserch);
        editText = (EditText)findViewById(R.id.gettekst);

       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (isOnline()) {
                   method3(GlobalVar.getUserID(),"searchnews");
               } else {
                   Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
               }
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
        listView = (ListView) findViewById(R.id.lististdssd);

/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (totalList.get(i).getServiceType().equals("1")) {

                    //getApplication()
                    Log.i("adfffgree", totalList.get(i).getServiceType());
                    Intent intent = new Intent(PageMoshaverin.this, ExplainMoshaver.class);
                    intent.putExtra("ServiceID", totalList.get(i).getServiceID());
                    //finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    *//*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*//*
                    startActivity(intent);     }
                else if(totalList.get(i).getServiceType().equals("0")) {
                    Intent intent=new Intent(getApplication(), ExplainMoshaver.class);
                    startActivity(intent);
                }
            }
        });*/

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);


    }
    void method3(final String userid ,final String fnname) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
        progressbarsandaha.setVisibility(View.VISIBLE);
        final TextView textView=(TextView)findViewById(R.id.myTextProgress);
        textView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);

        MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://iusnews.ir/webservicesmahan/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("qwertyuioaladfffgree", response);

                List<HalatiKeYekNist> templist=new ArrayList<>();
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
                        HalatiKeYekNist temp = new HalatiKeYekNist(s.get("ID").toString(),s.get("ContentType").toString(),s.get("Lead").toString(),s.getString("Title").toString(),
                                s.getString("PreTitle").toString(),s.getString("PTime").toString(),s.getString("PicURL").toString(),s.getString("Sort").toString());                        templist.add(temp);
                    }
                    totalList.addAll(templist);
                    if(tempcount==0){
                        adapter =new ItemHalatiKeTypeYekNist(serch.this,totalList);  //deghat dar sorat taghir name class
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

                listView.setVisibility(View.VISIBLE);

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
                MyData.put("fnname", "searchnews");
                MyData.put("userid", GlobalVar.getUserID());
                MyData.put("start","0");
                MyData.put("text",editText.getText().toString());

                Log.i("qwertyuioaladfffgree", MyData.toString());
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