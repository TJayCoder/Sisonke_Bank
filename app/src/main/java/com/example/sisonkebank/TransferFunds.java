package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

public class TransferFunds extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    //Declaring Widgets
    Toolbar toolbar;
    TextView userAccountData;
    EditText amount;
    Button transfer;
    DatabaseHelper databaseHelper;
    Bank_User bank_user;
    Spinner spin;


    //declaring Variables
    String[] AccountType = {"Current to Savings","Savings to Current"};
    public String Email;
    Cursor cursor;
    StringBuilder stringBuilder;
    String Choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_funds);

        //instance of Toolbar
        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //instance of widgets
        userAccountData=findViewById(R.id.tvUserData);
        transfer=findViewById(R.id.btnTransferFunds);
        amount=findViewById(R.id.etAmount);


        // this editText will only take numbers
        amount.setKeyListener(DigitsKeyListener.getInstance("1234567890"));

        //instance of databaseHelper
        databaseHelper = new DatabaseHelper(TransferFunds.this);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the transfer options
        ArrayAdapter list = new ArrayAdapter(this,android.R.layout.simple_spinner_item,AccountType);
        list .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(list );


        //Passing Intent of user Email from the Login Activity
        Email= getIntent().getStringExtra("UserEmail");
         cursor = databaseHelper.getUserDetails(Email);

         stringBuilder = new StringBuilder();

         //Retrieving User current balance and Savings Account balance from the database
        while (cursor.moveToNext()) {

            final StringBuilder append = stringBuilder.append("Current Account Balance: " + cursor.getString(6) +"\n\n");

            final StringBuilder append2 =stringBuilder.append("  Savings Account Balance: " + cursor.getString(7));
        }

        //setting value to  a textview
        userAccountData.setText(stringBuilder);





        transfer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String Amount =amount.getText().toString().trim();

                Choice = spin.getSelectedItem().toString();



                    if (Amount.isEmpty()) {

                        Toast.makeText(TransferFunds.this, "Enter the Amount to be Transferred", Toast.LENGTH_SHORT).show();


                    }
                    else {
                        Cursor cursor = databaseHelper.getUserDetails(Email);

                        // getting user balance and current balance
                        while (cursor.moveToNext()) {
                            String savings = cursor.getString(6);
                            String current = cursor.getString(7);


                            try {


                                double NewBalanceSav = 0;
                                double NewBalanceCurrent = 0;

                                //check if the user selected Saving to Current or Current to Savings from the Spinner
                                if (Choice == "Savings to Current") {

                                    // check if the Savings account has enough cash to transfer
                                    if (Integer.parseInt( savings)==0 ) {

                                        Toast.makeText(TransferFunds.this, "Insufficient funds !!", Toast.LENGTH_SHORT).show();

                                    }else{



                                    // calculate based on the user's choice
                                    NewBalanceSav = Integer.parseInt(savings) - Double.parseDouble(Amount);
                                    NewBalanceCurrent = Integer.parseInt(current) + Double.parseDouble(Amount);


                                    //setting calculated values to  a textview
                                    userAccountData.setText("Current Account Balance: " + NewBalanceCurrent + "\n\n" + "  Savings Account Balance: " + NewBalanceSav);

                                    //passing the calculated values to update the database with new values
                                    boolean b = databaseHelper.updateBalance(NewBalanceCurrent, NewBalanceSav, Email);
                                    if (b == true) {

                                        Toast.makeText(TransferFunds.this, "Amount Transferred", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(TransferFunds.this, "Error!!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                } else {


                                    //check if the Current account has enough cash to transfer
                                    if (Integer.parseInt( current)==0) {

                                        Toast.makeText(TransferFunds.this, "Insufficient funds !!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        // calculate based on the user's choice
                                        NewBalanceSav = Integer.parseInt(savings) + Double.parseDouble(Amount);
                                        NewBalanceCurrent = Integer.parseInt(current) - Double.parseDouble(Amount);

                                        //setting calculated values to  a textview
                                        userAccountData.setText("Current Account Balance: " + NewBalanceCurrent + "\n\n" + "  Savings Account Balance: " + NewBalanceSav);

                                        //passing the calculated values to update the database with new values
                                        boolean b = databaseHelper.updateBalance(NewBalanceCurrent, NewBalanceSav, Email);
                                        if (b == true) {
                                            Toast.makeText(TransferFunds.this, "Amount Transferred", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(TransferFunds.this, "Error!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            } catch (NullPointerException e) {

                                System.out.println(e.getMessage());

                            }

                        }


                    }

            }

        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
