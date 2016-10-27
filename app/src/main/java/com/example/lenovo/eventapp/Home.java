package com.example.lenovo.eventapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class Home extends AppCompatActivity {

    DatabaseHelper mydb;
    //public static SQLiteDatabase db;
    private  EditText email;
    private  Button proceed_btn;
    // User Session Manager Class
    UserSessionManager session;
    int attempt_counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // User Session Manager
        mydb=new DatabaseHelper(this);
        session = new UserSessionManager(getApplicationContext());

        email= (EditText)findViewById(R.id.editText_email);
        proceed_btn=(Button)findViewById(R.id.button_proceed);
        //Toast.makeText(getApplicationContext(),"User Login Status: " + session.isUserLoggedIn(),Toast.LENGTH_LONG).show();
        email.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus==true)
                {
                    if (email.getText().toString().compareTo("Enter Email ID")==0)
                    {
                        email.setText("");
                    }
                }
            }
        });


        proceed_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        String substrName=email.getText().toString().substring(0,email.getText().toString().indexOf("@"));
                        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        Calendar c = Calendar.getInstance();
                        //SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        //Calendar c = Calendar.getInstance();
                        Boolean updateMe = false;
                        String memberEmail="";
                        String memberName="";
                        String memberId="";
                        Boolean insertMe = false;

                        String formattedDate = c.get(Calendar.YEAR) + "/"
                                + c.get(Calendar.MONTH)
                                + "/" + c.get(Calendar.DAY_OF_MONTH)
                                + " " + c.get(Calendar.HOUR_OF_DAY)
                                + ":" + c.get(Calendar.MINUTE);
                        //String formattedDate = df.format(c.getTime());

                        Cursor res = mydb.getMemberData();
                        Log.i("table count","memebers" + res.getCount());

                        if (res.getCount()== 0)
                        {
                            //mydb.onCreate(db);
                            Log.i("table count","memebers 2" + res.getCount());
                            Toast.makeText(Home.this,"No Data found",Toast.LENGTH_SHORT).show();
                            boolean isMemberInserted = mydb.insertMemberData(substrName, email.getText().toString(), "", 1, 0, formattedDate, formattedDate);

                            if (isMemberInserted == true)
                            {
                                Toast.makeText(Home.this, "Thank you for Signing up", Toast.LENGTH_SHORT).show();
                                session.createUserLoginSession("EventApp", email.getText().toString());

                                // Starting MainActivity
                                Intent i = new Intent(getApplicationContext(), User.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);

                                finish();
                            }
                            else
                            {
                                Toast.makeText(Home.this, "Something went wrong!! Couldn't Signup", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            while (res.moveToNext())
                            {
                                Boolean emailMatch =  res.getString(2).equals(email.getText().toString());
                                Log.i("emailMatch", String.valueOf(emailMatch));
                               if (emailMatch)
                               {
                                   memberEmail = res.getString(2);
                                   memberName = res.getString(1);
                                   memberId = String.valueOf(res.getInt(0));
                                   updateMe = true;
                               }
                                else
                               {
                                   Log.i("email doesn't exist", email.getText().toString());
                                   memberEmail = email.getText().toString();
                                   memberName = substrName;
                                   insertMe = true;
                               }
                            }

                        }

                        if(updateMe == true)
                        {
                            boolean isMemberUpdated = mydb.updateMemberData(memberId,formattedDate);
                            updateMe = false;
                            if (isMemberUpdated == true)
                                Toast.makeText(Home.this, "Welcome Back " + memberName, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Home.this, "Member Data not Updated", Toast.LENGTH_SHORT).show();

                            session.createUserLoginSession("EventApp", email.getText().toString());

                            // Starting MainActivity
                            Intent i = new Intent(getApplicationContext(), User.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();

                        }
                        else if (insertMe == true)
                        {
                            boolean isMemberInserted = mydb.insertMemberData(memberName,memberEmail, "", 1, 0, formattedDate, formattedDate);

                            if (isMemberInserted == true)
                            {
                                Toast.makeText(Home.this, "Thank you for Signing up", Toast.LENGTH_SHORT).show();
                                session.createUserLoginSession("EventApp", email.getText().toString());
                                insertMe = false;
                                // Starting MainActivity
                                Intent i = new Intent(getApplicationContext(), User.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Home.this, "Something went wrong!! Couldn't Signup", Toast.LENGTH_SHORT).show();
                            }
                        }
                        /*if (email.getText().toString().equals("m.rajvel@gmail.com"))
                        {
                            Toast.makeText(Home.this,"Emil ID is correct",Toast.LENGTH_SHORT).show();
                            //Intent intent= new Intent("com.example.lenovo.eventapp.User");
                            //startActivity(intent);
                            // Creating user login session
                            // Statically storing name="Android Example"
                            // and email="androidexample84@gmail.com"
                            session.createUserLoginSession("EventApp",email.getText().toString());

                            // Starting MainActivity
                            Intent i = new Intent(getApplicationContext(), User.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                            finish();

                        }
                        else
                        {
                            Toast.makeText(Home.this,"Email ID is not correct",Toast.LENGTH_SHORT).show();
                            attempt_counter--;
                            if(attempt_counter==0)
                            {
                                proceed_btn.setEnabled(false);
                            }
                        }*/

                    }

                }
        );


    }

}
