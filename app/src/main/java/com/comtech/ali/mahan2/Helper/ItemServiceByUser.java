package com.comtech.ali.mahan2.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.comtech.ali.mahan2.R;
import com.comtech.ali.mahan2.model.GlobalVar;
import com.comtech.ali.mahan2.model.ServicesByUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Techno Service on 7/24/2017.
 */
public class ItemServiceByUser extends ArrayAdapter<ServicesByUser>{

    List<ServicesByUser> Servicename=new ArrayList<>();

    ItemServiceByUser adapter;
    private Context mContext;
    private List<ServicesByUser> reportItemList = new ArrayList<>();
    String[] servcelar= new String[4];
    ArrayList<String> serviceslar = new ArrayList<String>();


    public ItemServiceByUser(Context context, List<ServicesByUser> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if(position== GlobalVar.SELECTED_ITEM_SICH_OLUB){
            view = vi.inflate(R.layout.item_shmarkaz, null);
        }else{
            view = vi.inflate(R.layout.item_markaz, null);
        }
        ServicesByUser Ittem = reportItemList.get(position);

        TextView Name = (TextView) view.findViewById(R.id.groupName);
        Name.setText((CharSequence) Ittem.getService());


        return view;
    }


}












