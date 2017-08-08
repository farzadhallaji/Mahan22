package com.comtech.ali.mahan2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comtech.ali.mahan2.Helper.ItemServiceByUser;
import com.comtech.ali.mahan2.model.GlobalVar;
import com.comtech.ali.mahan2.model.ServicesByUser;
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
/**
 * Created by Ali on 7/25/2017.
 */


public class ListServicesByUser extends AppCompatActivity {
    View ftView;
    ItemServiceByUser adapter;
    List<ServicesByUser> totalList=new ArrayList<>();
    int tempcount=0;
    private RequestQueue MyRequestQueue;
    ListView expandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_layout);
        Fabric.with(this, new Crashlytics());


        Button close=(Button)findViewById(R.id.colseBtn);
        expandableListView=(ListView)findViewById(R.id.expanded_menu);

        DisplayMetrics displayMetrics = new DisplayMetrics();     //Pop-up
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int length=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*0.85),(int)(length*0.65));

        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(ListServicesByUser.this,PageMoshaverin.class);
                startActivity(intent);
            }
        });

        if (isOnline()) {
            method2(GlobalVar.getUserID(),"getservicesbyuser");
            expandableListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setSelected(true);
                    expandableListView.setItemChecked(i, true);
                }
            });
          /*  ListeMarakezListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ListeMarakez.this,ExplainMarkaz.class);
                    intent.putExtra("placeid",markazs.get(position).getMID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    *//*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*//*
                    //intent.putExtra("Mainplace",markazs.get(position));
                    startActivity(intent);

                }
            });*/


        } else {
            Toast.makeText(getApplicationContext(), "Internet isn't available !", Toast.LENGTH_LONG).show();
        }
    }


    void method2(final String userid ,final String fnname) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
        progressbarsandaha.setVisibility(View.VISIBLE);
     //   textView=(TextView)findViewById(R.id.myTextProgress);
       // textView.setVisibility(View.VISIBLE);



        MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://iusnews.ir/webservicesmahan/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //The String 'response' contains the server's response.
                Log.i("qwfree", response);

                List<ServicesByUser> templist = new ArrayList<>();
                Log.i("saasasa", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject s = (JSONObject) jsonArray.get(i);
                        // List<String>tag=new ArrayList<>();
                        //JSONArray tags = (JSONArray) s.get("Tag");
                        //Toast.makeText(getApplicationContext(),String.valueOf(tags), Toast.LENGTH_LONG).show();
                        //for(int j= 0 ;j < tags.length() ; j++){

                        //  tag.add(tags.get(j).toString());
                        //Toast.makeText(getApplicationContext(),tags.get(j).toString(), Toast.LENGTH_LONG).show();
                        //      }
                        ServicesByUser temp = new ServicesByUser(s.get("Service").toString(), s.get("ServiceType").toString(), s.get("ServiceID").toString());
                        templist.add(temp);
                    }
                    totalList.addAll(templist);
                    if (tempcount == 0) {
                        adapter = new ItemServiceByUser(ListServicesByUser.this, totalList);  //deghat dar sorat taghir name class
                        expandableListView.setAdapter(adapter);
                        tempcount++;
                    }
                    adapter.notifyDataSetChanged();
                    // listView.removeFooterView(ftView);

                    ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
                    progressbarsandaha.setVisibility(View.INVISIBLE);
                    //textView.setVisibility(View.INVISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                //Log.i("asasasasasasa",adviseridm+"/"+GlobalVar.getDeviceID());

                MyData.put("fnname", "getservicesbyuser");
                MyData.put("userid", GlobalVar.getUserID());

                Log.i("qee",MyData.toString());//
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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





    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,PageMoshaverin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }
}
