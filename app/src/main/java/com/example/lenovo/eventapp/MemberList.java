package com.example.lenovo.eventapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MemberList extends AppCompatActivity {

    ListView memberListView;
    private MemberListDataAdapter memberAdapter;
    private List<MemberDataProvider> memberList;
    DatabaseHelper dbHelper;
    EditText txt_memberSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        dbHelper = new DatabaseHelper(this);
        memberListView = (ListView) findViewById(R.id.memberList_view);
        memberList = new ArrayList<>();

        Cursor res = dbHelper.getMemberData();
        if (res.getCount()== 0)
        {
            showMessage("Error","Data not found");
            return;
        }
        while(res.moveToNext())
        {
            memberList.add(new MemberDataProvider(res.getInt(0),res.getString(1),res.getString(2),res.getString(3),
                    res.getString(4),res.getString(5),res.getInt(6),res.getInt(7)));
        }
        //showMessage("Data",buffer.toString());
        memberAdapter = new MemberListDataAdapter(this,R.layout.member_row_layout,memberList);
        memberListView.setAdapter(memberAdapter);


        txt_memberSearch = (EditText) findViewById(R.id.memberSearch);

        // Capture Text in EditText
        txt_memberSearch.addTextChangedListener(
                new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String text = txt_memberSearch.getText().toString().toLowerCase(Locale.getDefault());
                        memberAdapter.filter(text);
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
