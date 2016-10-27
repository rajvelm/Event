package com.example.lenovo.eventapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by lenovo on 10/27/2016.
 */

public class MemberListDataAdapter extends ArrayAdapter<MemberDataProvider> {
    private List<MemberDataProvider> memberList = null;
    private ArrayList<MemberDataProvider> arraylist;
    private AppCompatActivity activity;
    private DatabaseHelper mydb = new DatabaseHelper(getContext());

    public MemberListDataAdapter(AppCompatActivity context, int resource, List<MemberDataProvider> objects) {
        super(context, resource, objects);
        this.memberList = objects;
        this.activity = context;
        this.arraylist = new ArrayList <MemberDataProvider>();
        this.arraylist.addAll(memberList);
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public MemberDataProvider getItem(int position) {
        return memberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MemberListDataAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.member_row_layout,parent,false);
            holder = new MemberListDataAdapter.ViewHolder(convertView);
            convertView.setTag(holder);

        } else
        {
            holder = (MemberListDataAdapter.ViewHolder) convertView.getTag();
        }


        holder.memberName.setText(memberList.get(position).getMemberName());
        holder.memberEmail.setText(memberList.get(position).getMemberEmail());
       /*holder.eventURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("List Item", "onClick: "+mEventList.get(position).getId());
            }
        });*/


        return convertView;
    }


    private static class ViewHolder {
        private TextView memberName;
        private TextView memberEmail;

        public ViewHolder(View view) {
            memberName = (TextView)view.findViewById(R.id.text_memberName);
            memberEmail = (TextView)view.findViewById(R.id.text_memberEmail);
        }

    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        memberList.clear();
        if (charText.length() == 0) {
            memberList.addAll(arraylist);
        }
        else
        {
            for (MemberDataProvider mdp : arraylist)
            {
                if (mdp.getMemberName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    memberList.add(mdp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
