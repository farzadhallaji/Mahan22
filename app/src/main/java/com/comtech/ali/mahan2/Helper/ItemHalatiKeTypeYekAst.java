package com.comtech.ali.mahan2.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comtech.ali.mahan2.R;
import com.comtech.ali.mahan2.model.HalatiKeTypeYekAst;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Techno Service on 8/5/2017.
 */
public class ItemHalatiKeTypeYekAst extends ArrayAdapter<HalatiKeTypeYekAst> {

    private Context mContext;
    private List<com.comtech.ali.mahan2.model.HalatiKeTypeYekAst> reportItemList = new ArrayList<HalatiKeTypeYekAst>();


    public ItemHalatiKeTypeYekAst(Context context, List<com.comtech.ali.mahan2.model.HalatiKeTypeYekAst> objects) {

        super(context, 0, objects);
        this.mContext = context;
        this.reportItemList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.get_movie, null);

        TextView Name = (TextView) view.findViewById(R.id.descrption);
        ImageView imageView = (ImageView) view.findViewById(R.id.picofmovie);
        HalatiKeTypeYekAst Ittem = reportItemList.get(position);
        Name.setText(Ittem.getTitle());
        new DownloadImageTask(imageView).execute(reportItemList.get(position).getPicURL());
        return view;
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
