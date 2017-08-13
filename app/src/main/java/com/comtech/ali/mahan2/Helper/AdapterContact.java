package com.comtech.ali.mahan2.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comtech.ali.mahan2.R;
import com.comtech.ali.mahan2.model.GlobalVar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fayzad on 8/12/17.
 */

public class AdapterContact extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> reportItemList = new ArrayList<String>();


    public AdapterContact(Context context, List<String> objects) {

        super(context, 0, objects);
        this.mContext = context;
        this.reportItemList = objects;
    }

    @Override
    public long getItemId(int position) {
        return position; // return 0 here means All items are the same;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_contact, null);

        TextView Name = (TextView) view.findViewById(R.id.textview);
        Name.setText(reportItemList.get(position));

        if(GlobalVar.SELECTED_ITEM_CONTACT==position){
            Name.setBackgroundColor(0XFF085CB7);
        }else {
            Name.setBackgroundColor(0XFF479AF4);
        }
        return view;
    }

}

