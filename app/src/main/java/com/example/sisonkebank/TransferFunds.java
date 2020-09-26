package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TransferFunds extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView userAccountInfor;
    EditText amount;
    Button transfer;
    DatabaseHelper databaseHelper;
    Bank_User bank_user;
    Spinner spin;


    String[] AccountType = {"Savings to Current","Current to Savings"};
    public String Email;
    Cursor cursor;
    StringBuilder stringBuilder ,stringBuilder2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_funds);

        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userAccountInfor=findViewById(R.id.tvUserData);


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

        userAccountInfor.setText(stringBuilder);





        transfer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               int Amount=Integer.parseInt(amount.getText().toString().trim());
                String Choice=spin.getSelectedItem().toString();

                int sav=0,cur=0;
                int NewBalanceSav=0,NewBalanceCurrent=0;


                if(Choice=="Savings to Current"){

                    //calculation from saving to current account
                   NewBalanceSav= (int) (sav+Amount);
                    NewBalanceCurrent= (int) (sav-Amount);


                    boolean b =databaseHelper.updateBalance(NewBalanceSav,NewBalanceCurrent,Email);
                    if(b==true){
                        Toast.makeText(TransferFunds.this, "Amount Transferred", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TransferFunds.this, "Error!!", Toast.LENGTH_SHORT).show();
                    }

                }else {

                    //calculation from  current to saving  account
                    NewBalanceSav= (int) (sav-Amount);
                    NewBalanceCurrent= (int) (sav+Amount);


                    boolean b =   databaseHelper.updateBalance(NewBalanceSav,NewBalanceCurrent,Email);
                    if(b==true){
                        Toast.makeText(TransferFunds.this, "Amount Transferred", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TransferFunds.this, "Error!!", Toast.LENGTH_SHORT).show();
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
