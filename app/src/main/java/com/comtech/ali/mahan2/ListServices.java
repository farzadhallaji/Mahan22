package com.comtech.ali.mahan2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.comtech.ali.mahan2.Helper.ItemServices;
import com.comtech.ali.mahan2.internet.HttpManager;
import com.comtech.ali.mahan2.internet.RequestPackage;
import com.comtech.ali.mahan2.model.GlobalVar;
import com.comtech.ali.mahan2.model.Services;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Techno Service on 7/25/2017.
 */
public class ListServices extends AppCompatActivity{


    GridView listeServies;
    List<Services> services=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_liste_moshaverin);
     //   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        listeServies = (GridView)findViewById(R.id.gridview);
        if (isOnline()) {
            requestData(GlobalVar.getUserID(),"getservices");

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

    private void requestData(String userid,String fnname) {

        //ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        //progressbarsandaha.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams();
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");

        p.setUri("http://iusnews.ir/webservicesmahan/");

        params.put("userid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
        params.put("fnname", "getservices");


        Log.i("qwertyuio",p.getParams().toString());
        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

    }

private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


    @Override
    protected String doInBackground(RequestPackage... params) {
        String content = HttpManager.getData(params[0]);
        return content;
    }

    @Override
    protected void onPostExecute(String result) {

      /*  //Toast.makeText(getApplicationContext(), "0"+result+"0", Toast.LENGTH_LONG).show();
        *//**//*Log.i("ahmadigum","0000ahmadigum");
        Log.i("ahmadigum",result);
        *//**//*
        Log.i("qwertyuio", result);*/

        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject tmp;
            services= new ArrayList<>();
            String Services,ServiceType,ServiceID,IconURL="";
            if(jsonArray.length()>0){
                for(int i = 0 ; i < jsonArray.length() ; i++){

                    try {
                        tmp= (JSONObject) jsonArray.get(i);
                        ServiceID=tmp.getString("ServiceID");
                        ServiceType=tmp.getString("ServiceType");
                        Services=tmp.getString("Service");
                        IconURL=tmp.getString("IconURL");

                        Log.i("asdfghfgfvdcynbfv",result);
                        Services Serviceha = new Services(Services,ServiceID,ServiceType,IconURL);
                        services.add(Serviceha);
                    }catch (Exception ignored){}
                    ItemServices listeSrevices = new ItemServices(ListServices.this,services);
                    listeServies.setAdapter(listeSrevices);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        //progressbarsandaha.setVisibility(View.INVISIBLE);


    }
}
    @Override
    public void onBackPressed() {
        finish();

    }
}


/*
if (isOnline()) {
        requestData("0", "0", "0", "0", "0");

        ListeMarakezListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });


        } else {
        Toast.makeText(getApplicationContext(), "internet isn't available !", Toast.LENGTH_LONG).show();
        }


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

private void requestData(String lat ,String lon ,String start , String adviserid , String subjectid ) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        //p.setUri("http://telyar.dmedia.ir/webservice/Get_all_subject");

        p.setUri("http://telyar.dmedia.ir/webservice/Get_mainplace");

        p.setParam("lat",  String.valueOf(lat));
        p.setParam("lon", String.valueOf(lon));
        p.setParam("adviserid", String.valueOf(adviserid));
        p.setParam("subjectid", String.valueOf(subjectid));
        p.setParam("start", String.valueOf(start));
        p.setParam("deviceid", GlobalVar.getDeviceID());


        Log.i("qwertyuio",p.getParams().toString());
        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

        }

private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


    @Override
    protected String doInBackground(RequestPackage... params) {
        String content = HttpManager.getData(params[0]);
        return content;
    }

    @Override
    protected void onPostExecute(String result) {

        //Toast.makeText(getApplicationContext(), "0"+result+"0", Toast.LENGTH_LONG).show();
            *//*Log.i("ahmadigum","0000ahmadigum");
            Log.i("ahmadigum",result);
*//*
        Log.i("qwertyuio",result);

        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject tmp;
            markazs= new ArrayList<>();
            String PicAddress , MID, Address, AboutMainPlace,MainPlaceName,Telephone,Lat,Long,Distance="";
            if(jsonArray.length()>0){
                for(int i = 0 ; i < jsonArray.length() ; i++){

                    try {
                        tmp= (JSONObject) jsonArray.get(i);
                        PicAddress=tmp.getString("PicAddress");
                        MID=tmp.getString("MID");
                        Address=tmp.getString("Address");
                        AboutMainPlace=tmp.getString("AboutMainPlace");
                        MainPlaceName=tmp.getString("MainPlaceName");
                        Telephone=tmp.getString("Telephone");
                        Lat=tmp.getString("Lat");
                        Long=tmp.getString("Long");
                        Distance=tmp.getString("Distance");
                        //Log.i("asdfghfgfvdcynbfv",result);
                        Markaz markaz = new Markaz(Lat,  Long,  PicAddress,  MID,  Address,
                                AboutMainPlace,  MainPlaceName, Telephone,  Distance);
                        markazs.add(markaz);
                    }catch (Exception ignored){}
                    ListemarakezAdapter listemarakezAdapter = new ListemarakezAdapter(ListeMarakez.this,markazs);
                    ListeMarakezListView.setAdapter(listemarakezAdapter);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.INVISIBLE);


    }

}*/