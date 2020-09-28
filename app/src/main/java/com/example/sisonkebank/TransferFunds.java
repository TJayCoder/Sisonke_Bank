package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.service.autofill.UserData;
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

    Toolbar toolbar;
    TextView userAccountData;
    EditText amount;
    Button transfer;
    DatabaseHelper databaseHelper;
    Bank_User bank_user;
    Spinner spin;



    String[] AccountType = {"Savings to Current","Current to Savings"};
    public String Email;
    Cursor cursor;
    StringBuilder stringBuilder;
    String Choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_funds);

        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userAccountData=findViewById(R.id.tvUserData);


        transfer=findViewById(R.id.btnTransferFunds);
        amount=findViewById(R.id.etAmount);

        databaseHelper = new DatabaseHelper(TransferFunds.this);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter list = new ArrayAdapter(this,android.R.layout.simple_spinner_item,AccountType);
        list .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(list );


        //Receiving Intent from the Login Activity

        Email= getIntent().getStringExtra("UserEmail");
         cursor = databaseHelper.getUserDetails(Email);

         stringBuilder = new StringBuilder();

        while (cursor.moveToNext()) {

            final StringBuilder append = stringBuilder.append("Current Account Balance: " + cursor.getString(6) +"\n\n");

            final StringBuilder append2 =stringBuilder.append("  Savings Account Balance: " + cursor.getString(7));
        }

        userAccountData.setText(stringBuilder);





        transfer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               int Amount=Integer.parseInt(amount.getText().toString().trim());
               Choice=spin.getSelectedItem().toString();

                Cursor cursor=databaseHelper.getUserDetails(Email);


                while(cursor.moveToNext()){
                 String savings=cursor.getString(6);
                String current=cursor.getString(7);


                    try {
                        double NewBalanceSav=0;
                        double NewBalanceCurrent=0;

                        if(Choice=="Savings to Current") {



                             NewBalanceSav=Integer.parseInt( savings)-Amount;
                             NewBalanceCurrent=Integer.parseInt(current)+Amount;



                             userAccountData.setText("Current Account Balance: " + NewBalanceCurrent   +"\n\n"+    "  Savings Account Balance: " + NewBalanceSav);

                            boolean b = databaseHelper.updateBalance(NewBalanceCurrent,NewBalanceSav, Email);
                            if (b == true) {

                                Toast.makeText(TransferFunds.this, "Amount Transferred", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(TransferFunds.this, "Error!!", Toast.LENGTH_SHORT).show();
                            }

                        }else{

                              NewBalanceSav=Integer.parseInt( savings)+Amount;
                               NewBalanceCurrent=Integer.parseInt(current)-Amount;


                             userAccountData.setText("Current Account Balance: " + NewBalanceCurrent   +"\n\n"+    "  Savings Account Balance: " + NewBalanceSav);

                            boolean b = databaseHelper.updateBalance(NewBalanceCurrent,NewBalanceSav, Email);
                            if (b == true) {
                                Toast.makeText(TransferFunds.this, "Amount Transferred", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TransferFunds.this, "Error!!", Toast.LENGTH_SHORT).show();
                            }
                        }




                    }catch(NullPointerException e){

                        System.out.println(e.getMessage());

                    }

                }









            }

        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position==1){



          //   Toast.makeText(getApplicationContext(),AccountType[position] , Toast.LENGTH_LONG).show();



        }else
            {
               //  Toast.makeText(getApplicationContext(),AccountType[position] , Toast.LENGTH_LONG).show();


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
