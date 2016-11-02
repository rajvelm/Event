package com.example.lenovo.eventapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class Gallery extends AppCompatActivity {

    GridView gvGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gvGallery=(GridView)findViewById(R.id.gridviewGallery);
        gvGallery.setAdapter(new ImageAdapter(this));
        gvGallery.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getBaseContext(),"You click on image "+position,Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
