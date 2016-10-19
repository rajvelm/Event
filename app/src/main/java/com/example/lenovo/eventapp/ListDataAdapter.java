package com.example.lenovo.eventapp;

/**
 * Created by lenovo on 8/26/2016.
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 8/23/2016.
 */
public class ListDataAdapter extends ArrayAdapter<DataProvider> {

    private List<DataProvider> mEventList;
    private AppCompatActivity activity;
    private DatabaseHelper mydb = new DatabaseHelper(getContext());

    public ListDataAdapter(AppCompatActivity context, int resource, List<DataProvider> objects) {
        super(context, resource, objects);
        this.mEventList = objects;
        this.activity = context;
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Override
    public DataProvider getItem(int position) {
        return mEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_layout,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.ratingBar.getTag(position);
        }

        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));

        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(mEventList.get(position).getEventRating());
        holder.eventName.setText(mEventList.get(position).getEventName());
        /*holder.eventDesc.setText(mEventList.get(position).getEventDesc());
        holder.eventDate.setText(mEventList.get(position).getEventDate());
        holder.eventCreatedBy.setText(mEventList.get(position).getEventCreatedBy());*/
        //holder.eventURL.setText(mEventList.get(position).getEventUrl());


        /*holder.eventURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("List Item", "onClick: "+mEventList.get(position).getId());
            }
        });*/

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_eventDetail);
        imageView.setTag(new Integer(position));
        imageView.setOnClickListener(
                //Toast.makeText(getContext(), "ImageView clicked for the row = "+mEventList.get(position).getId(), Toast.LENGTH_SHORT).show();
                new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               Intent eventDetail_intent=new Intent(getContext(),EventDetailView.class);
                eventDetail_intent.putExtra("event_id",mEventList.get(position).getId());
                eventDetail_intent.putExtra("event_name",mEventList.get(position).getEventName());
                eventDetail_intent.putExtra("event_url",mEventList.get(position).getEventUrl());
                eventDetail_intent.putExtra("event_rating",mEventList.get(position).getEventRating());
                eventDetail_intent.putExtra("event_desc",mEventList.get(position).getEventDesc());
                eventDetail_intent.putExtra("event_date",mEventList.get(position).getEventDate());
                eventDetail_intent.putExtra("event_created",mEventList.get(position).getEventCreatedBy());
                activity.startActivity(eventDetail_intent);

            }
        });


        return convertView;
    }



    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override

            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //ratingBar.setRating(mEventList.get(position).getEventRating());
                //HashMap mRatings = null;
                DataProvider item = getItem(position);
                //int rateValue = (int)v;
                //UpdateRating(position,v);
                //mRatings.add(position,v);

                item.setEventRating(v);
                /*Drawable stars = (Drawable) ratingBar.getProgressDrawable();
                DrawableCompat.setTint(stars, Color.YELLOW);*/
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.parseColor("#800000"), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(Color.parseColor("#8e8b8b"), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(Color.parseColor("#8e8b8b"), PorterDuff.Mode.SRC_ATOP);

                //DrawableCompat.
                //stars.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                //stars.(Color.YELLOW);
                //Log.i("adapter", "onRatingChanged:star "+v);
                boolean isUpdated = mydb.updateData(String.valueOf(mEventList.get(position).getId()),
                        mEventList.get(position).getEventName(),String.valueOf(v),mEventList.get(position).getEventUrl());

                /*if (isUpdated == true)
                    Log.i("Update rating", "onRatingChanged: true ");
                else
                    Log.i("Update rating", "onRatingChanged: false ");*/
            }
        };
    }

    private static class ViewHolder {
        private RatingBar ratingBar;
        //private TextView movieName;
        private TextView eventName;
        private TextView eventURL;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.eventRating);
            //movieName = (TextView) view.findViewById(R.id.text);
            eventName = (TextView)view.findViewById(R.id.text_eventName);
            //eventURL = (TextView)view.findViewById(R.id.text_eventUrl);
        }

    }

}

