package com.example.lenovo.eventapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Publish extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    DatabaseHelper mydb;
    Button submit_btn, datetime_btn;
    //Button view_btn;
    //Button update_btn;
    EditText txt_eventname,txt_eventurl, txt_eventdesc, txt_eventdatetime;
    Spinner eventType_spinner;
    String txt_eventType="";
    UserSessionManager session;
    int day,month,year,hour,minute;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        eventType_spinner=(Spinner)findViewById(R.id.eventType);

        session = new UserSessionManager(getApplicationContext());
        List<String> eventType_array = new ArrayList<String>();

        eventType_array.add("Learn");
        eventType_array.add("Social");
        eventType_array.add("Achieve");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventType_array);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        eventType_spinner.setAdapter(dataAdapter);

        mydb=new DatabaseHelper(this);
        submit_btn=(Button)findViewById(R.id.button_publish);
        //view_btn =(Button)findViewById(R.id.button_viewEventList);
        //update_btn = (Button)findViewById(R.id.button_update);
        datetime_btn=(Button)findViewById(R.id.button_datetimepicker);

        txt_eventname=(EditText)findViewById(R.id.editText_eventName);
        //txt_eventurl=(EditText)findViewById(R.id.editText_eventUrl);
        txt_eventdesc =(EditText)findViewById(R.id.editText_eventDesc);
        txt_eventdatetime=(EditText)findViewById(R.id.editText_eventDatetime);

        datetime_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Calendar c= Calendar.getInstance();
                        year=c.get(Calendar.YEAR);
                        month=c.get(Calendar.MONTH);
                        day=c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog=new DatePickerDialog(Publish.this,Publish.this,year,month,day);
                        datePickerDialog.show();
                    }
                }
        );
        //txt_eventType =

        eventType_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        txt_eventType = parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(),"Selected :"+txt_eventType, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        submit_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        HashMap<String, String> user = session.getUserDetails();
                        boolean isInserted = mydb.insertData(txt_eventname.getText().toString(), user.get(UserSessionManager.KEY_EMAIL),
                                "https://www.youtube.com/user/mrajvel",txt_eventdesc.getText().toString(), txt_eventdatetime.getText().toString(), "video id",txt_eventType );

                        Log.i("Event Type", "button click: "+txt_eventType);

                        if (isInserted == true)
                            Toast.makeText(Publish.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Publish.this, "Data not Inserted", Toast.LENGTH_SHORT).show();

                        //Log.i("event name", "onClick: "+txt_eventname.getText().toString());
                        //Log.i("event url", "onClick: "+txt_eventurl.getText().toString());
                    }
                }
        );

        //AddData();
        // ViewData();
        //ViewEventList();
        //UpdateEventData();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog=new TimePickerDialog(Publish.this,Publish.this,hour,minute,DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        txt_eventdatetime.setText(yearFinal+"/"+monthFinal+"/"+dayFinal+"  "+hourFinal+":"+minuteFinal);
    }


    /*public void ViewEventList()
    {
        view_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Intent listIntent = new Intent(Publish.this,DataListActivity.class);
                        startActivity(listIntent);
                    }
                }
        );
    }
*/

/*    public void UpdateEventData()
    {
        update_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        boolean isUpdated = mydb.updateData(txt_eventname.getText().toString(), txt_eventurl.getText().toString());

                        if (isUpdated == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }
   public void ViewData()
    {
        view_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Cursor res= mydb.getAllData();
                        if (res.getCount()== 0)
                        {
                            showMessage("Error","Data not found");
                            return;
                        }
                        //StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext())
                        {
                           buffer.append("ID :"+res.getString(0)+"\n");
                            buffer.append("Event Name :"+res.getString(1)+"\n");
                            buffer.append("Event URL :"+res.getString(4)+"\n\n");
                            mEventList.add(new DataProvider(res.getInt(0),res.getString(1),res.getInt(3),res.getString(4)));

                        }
                        //showMessage("Data",buffer.toString());
                        adapter = new ListDataAdapter(getApplicationContext(),mEventList);
                        lvEvent.setAdapter(adapter);




                    }
                }
        );


    } */
}
