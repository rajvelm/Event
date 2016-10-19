package com.example.lenovo.eventapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataListActivity extends AppCompatActivity{

    //SQLiteDatabase mydb;
    DatabaseHelper dbHelper;

    //event List View
    private ListView lvEvent;
    private ListDataAdapter adapter;
    private List<DataProvider> mEventList;
    private RatingBar rating_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_data_list);
        lvEvent = (ListView)findViewById(R.id.List_view);
        mEventList = new ArrayList<>();
        rating_b = (RatingBar)findViewById(R.id.eventRating);

        Cursor res = dbHelper.getAllData();
        if (res.getCount()== 0)
        {
            showMessage("Error","Data not found");
            return;
        }
        while(res.moveToNext())
        {
            mEventList.add(new DataProvider(res.getInt(0),res.getString(1),res.getString(2),res.getFloat(3),res.getString(4),res.getString(5),res.getString(6)));
        }
        //showMessage("Data",buffer.toString());
        adapter = new ListDataAdapter(this,R.layout.row_layout,mEventList);
        lvEvent.setAdapter(adapter);
        lvEvent.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(DataListActivity.this,"Position"+position,Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void showMessage(String title,String msg)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

}
