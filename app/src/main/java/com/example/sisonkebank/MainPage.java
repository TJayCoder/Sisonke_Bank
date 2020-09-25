package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainPage extends AppCompatActivity {

    Toolbar toolbar;
    Button viewbalance ,transfer,logout;
    TextView Message;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toolbar=findViewById(R.id.mytoolbar);
        viewbalance=findViewById(R.id.btnAccountBalance);
        transfer=findViewById(R.id.btnTransfer);
        logout=findViewById(R.id.btnLogout);
        Message=findViewById(R.id.tvWelcomMessage);
        databaseHelper=new DatabaseHelper(MainPage.this);


        //Receiving Intent from the Login Activity
        String Email=getIntent().getStringExtra("Email");
        Cursor cursor=databaseHelper.getUserDetails(Email);



        StringBuilder stringBuilder=new StringBuilder();
        while(cursor.moveToNext()){
            //Retrieve the Name from the database
            stringBuilder.append("Welcome "+ cursor.getString(0));
        }

        Message.setText(stringBuilder);



        setSupportActionBar(toolbar);
        userTransferFunds();
        userBankBalance();
        logoutMethod();

    }

    public void userTransferFunds(){
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainPage.this,TransferFunds.class);

                //Receiving Intent from the Login Activity
                String Email=getIntent().getStringExtra("Email");
                Cursor cursor=databaseHelper.getUserDetails(Email);

                //passing the email using Intent
                intent.putExtra("UserEmail",Email);

                startActivity(intent);
            }
        });
    }

    public void userBankBalance(){
        viewbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(MainPage.this,ViewBalance.class);

                //Receiving Intent from the Login Activity
                String Email=getIntent().getStringExtra("Email");
                Cursor cursor=databaseHelper.getUserDetails(Email);

                //passing the email using Intent
                intent.putExtra("UserEmail",Email);

                startActivity(intent);
            }
        });
    }
    public void logoutMethod(){

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //return to the login page and does not allow the user to use the back button to return online again
                Intent i = new Intent(MainPage.this,Login.class);
                startActivity(i);
                finish();
                Toast.makeText(MainPage.this, "Your Account has been logged-out", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
