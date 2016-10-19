package com.example.lenovo.eventapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class User extends AppCompatActivity {
    private static ImageButton event_btn;
    private static ImageButton publish_btn;
    private static ImageButton gallery_btn;
    private static ImageButton members_btn;
    private static TextView userEmail,userLogout;
    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        session = new UserSessionManager(getApplicationContext());

        userEmail = (TextView)findViewById(R.id.textView_emailId);
        userLogout = (TextView)findViewById(R.id.textView_logoutLink);

        event_btn=(ImageButton)findViewById(R.id.imageButton_event);
        publish_btn=(ImageButton)findViewById(R.id.imageButton_publish);
        gallery_btn=(ImageButton)findViewById(R.id.imageButton_media);
        members_btn=(ImageButton)findViewById(R.id.imageButton_members);

        //Toast.makeText(getApplicationContext(),"User Login Status: " + session.isUserLoggedIn(),Toast.LENGTH_LONG).show();

        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if(session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        //String name = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        // Show user data on activity
        //lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        userEmail.setText(Html.fromHtml("<b>" + email + "</b>"));

        userLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Clear the User session data
                // and redirect user to LoginActivity
                session.logoutUser();
            }
        });
        event_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent listIntent = new Intent(User.this,DataListActivity.class);
                        startActivity(listIntent);
                    }
                }
        );

        publish_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        //Toast.makeText(User.this,"event button click",Toast.LENGTH_SHORT).show();
                        Intent publish_intent=new Intent(User.this,Publish.class);
                        startActivity(publish_intent);
                    }
                }
        );

        gallery_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        //Toast.makeText(User.this,"event button click",Toast.LENGTH_SHORT).show();
                        Intent gallery_intent=new Intent(User.this,Gallery.class);
                        startActivity(gallery_intent);
                    }
                }
        );


        members_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        //Toast.makeText(User.this,"event button click",Toast.LENGTH_SHORT).show();
                        Intent members_intent=new Intent(User.this,MemberList.class);
                        startActivity(members_intent);
                    }
                }
        );
    }
}
