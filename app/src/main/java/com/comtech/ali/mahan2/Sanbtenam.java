package com.comtech.ali.mahan2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.comtech.ali.mahan2.model.GlobalVar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Techno Service on 8/11/2017.
 */
public class Sanbtenam extends Activity {
    EditText nem;
    private static AsyncHttpClient client = new AsyncHttpClient();
    EditText emel;
    EditText shomre;
    EditText sen;
    EditText shoghl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sabte_nam);

        EditText nem=(EditText)findViewById(R.id.neme);
        EditText emel=(EditText)findViewById(R.id.lele);
        EditText shomre=(EditText)findViewById(R.id.hele);
        EditText sen=(EditText)findViewById(R.id.pppp);
        EditText shoghl=(EditText)findViewById(R.id.popopopo);

        Button send=(Button)findViewById(R.id.ButtonSend);

        send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        requestDataaaaa();
    }
        });
    }
    private void requestDataaaaa() {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
        progressbarsandaha.setVisibility(View.VISIBLE);
        final TextView textView=(TextView)findViewById(R.id.myTextProgress);
        textView.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();

        params.put("name", nem.getText().toString()); //Add the data you'd like to send to the server.
        params.put("age", sen.getText().toString()); //Add the data you'd like to send to the server.
        params.put("job", shoghl.getText().toString()); //Add the data you'd like to send to the server.
        params.put("phone", shomre.getText().toString()); //Add the data you'd like to send to the server.
        params.put("email", emel.getText().toString()); //Add the data you'd like to send to the server.
        params.put("fnname", "mahanregister");

        client.post("http://iusnews.ir/webservicesmahan/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    Log.i("gfgfgtr", jsonObject.toString());

                    ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.myProgress);
                    progressbarsandaha.setVisibility(View.INVISIBLE);
                    final TextView textView=(TextView)findViewById(R.id.myTextProgress);
                    textView.setVisibility(View.INVISIBLE);

                    try {

                        String Message = jsonObject.getString("msg");
                        String s = jsonObject.getString("state");
                        String useridd = jsonObject.getString("userid");

                        if (s.equals("-1")) {
                            useridd = "";
                            Toast.makeText(getApplicationContext(),Message, Toast.LENGTH_LONG).show();

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        } else {
                            try {
                                String State = jsonObject.getString("state");
                                String UID = jsonObject.getString("userid");
                                String msg = jsonObject.getString("msg");


                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UID", UID).apply();
                                GlobalVar.setUserID(UID);
                                // Log.i("asdfghhgbv", "sefgtbrvfvdfvdfvlgklkgfgb1");
                                Toast.makeText(getApplicationContext(),Message, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Sanbtenam.this, PageMoshaverin.class);
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
                Log.i("rererrebfdfvv", "1");
            }
        });
    }
}
