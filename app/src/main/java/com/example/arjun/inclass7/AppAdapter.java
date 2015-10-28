package com.example.arjun.inclass7;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arjun on 10/5/2015.
 */
public class AppAdapter extends ArrayAdapter<Note>{

    Context mContext;
    int mResource;
    List<Note> mData;
    public AppAdapter(Context context, int resource, List<Note> objects) {
        //super(context);
        super(context, resource, objects);

        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
    }

    @Override // this is for every row in the list view
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        Note name = mData.get(position);

        TextView Appname = (TextView) convertView.findViewById(R.id.appname);
        Appname.setText(name.getAppName());
        TextView devname = (TextView) convertView.findViewById(R.id.devname);
        devname.setText(name.getDevName());
        TextView price = (TextView) convertView.findViewById(R.id.price);
        price.setText(name.getPrice());
        TextView category = (TextView) convertView.findViewById(R.id.category);
        category.setText(name.getCategory());
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(name.getDate());
        Picasso.with(mContext).load(name.getImgurl()).into((ImageView) convertView.findViewById(R.id.imageView));
        ImageView iv = (ImageView) convertView.findViewById(R.id.starimg);
        if(name.getIsfav().equals("isfav")){
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.goldstar);
            iv.setImageDrawable(drawable);
        }
        else {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.greystar);
            iv.setImageDrawable(drawable);
        }


        return convertView;
    }
}