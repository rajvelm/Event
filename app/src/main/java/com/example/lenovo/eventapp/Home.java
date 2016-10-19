package com.example.lenovo.eventapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    private static EditText email;
    private static Button proceed_btn;
    // User Session Manager Class
    UserSessionManager session;
    int attempt_counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // User Session Manager
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
                        if (email.getText().toString().equals("m.rajvel@gmail.com"))
                        {
                            /*Toast.makeText(Home.this,"Emil ID is correct",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent("com.example.lenovo.eventapp.User");
                            startActivity(intent);*/
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
                        }

                    }

                }
        );


    }

}
