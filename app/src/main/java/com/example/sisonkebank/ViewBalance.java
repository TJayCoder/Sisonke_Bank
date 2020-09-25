package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewBalance extends AppCompatActivity {

    Toolbar toolbar;
    TextView UserData;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_balance);


        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserData = findViewById(R.id.tvBalance);
        databaseHelper = new DatabaseHelper(ViewBalance.this);


            //Receiving Intent from the Login Activity
            String Email = getIntent().getStringExtra("UserEmail");
            Cursor cursor = databaseHelper.getUserDetails(Email);


            StringBuilder stringBuilder = new StringBuilder();
            while (cursor.moveToNext()) {


              //Retrieve the Name from the database
            stringBuilder.append("Account Holder Name: "+ cursor.getString(0)+" " +
                    "\n  \nAccount Holder Surname: "+ cursor.getString(1)+
                    " \n \nCurrent Account Balance: "+ cursor.getString(6)+
                    " \n \nSavings Account Balance: "+ cursor.getString(7));

            //
            }

            UserData.setText(stringBuilder);



    }


}
