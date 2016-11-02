package com.example.lenovo.eventapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by lenovo on 11/2/2016.
 */

public class ImageAdapter extends BaseAdapter {
    private Context ctx;

    private Integer image_id[]={
            R.drawable.sample01,
            R.drawable.sample02,
            R.drawable.sample03,
            R.drawable.sample04,
            R.drawable.sample05,
            R.drawable.sample06,
            R.drawable.sample07
    };

    public ImageAdapter(Context ctx)
    {
        this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return image_id.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView img;

        if(convertView == null)
        {
            img =new ImageView(ctx);
            img.setLayoutParams(new GridView.LayoutParams(250,250));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setPadding(8,8,8,8);
        }
        else
        {
            img= (ImageView)convertView;
        }
        img.setImageResource(image_id[position]);

        return img;
    }
}
