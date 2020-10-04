package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    Button btnRegister;
    TextView returnLogin;
    Toolbar toolbar;
    EditText name,surname,email,password,mobileNo;
    RadioButton gender;
    RadioGroup radioGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegister=findViewById(R.id.btnCreateAccount);
        returnLogin=findViewById(R.id.tvReturnLogin);

        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);
        name=findViewById(R.id.etName);
        surname=findViewById(R.id.etLastName);
        mobileNo=findViewById(R.id.etNumber);
        radioGroup=findViewById(R.id.radioGroupGender);

        toolbar=findViewById(R.id.mytoolbar);



        setSupportActionBar(toolbar);



        registerUser();
       returnLoginActivity();
    }

    public void registerUser(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  Email=email.getText().toString().trim();
                String   Password=password.getText().toString().trim();
                String   Name=name.getText().toString().trim();
                String  Surname=surname.getText().toString().trim();
                String  MobileNo=mobileNo.getText().toString().trim();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                gender= findViewById(selectedId);

               String Gender=gender.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String MobilePattern = "[0-9]{10}";

                    if(  Name.isEmpty())
                    {

                        Toast.makeText(Registration.this, "First Name Is Required", Toast.LENGTH_SHORT).show();

                    }
                    else if( Surname.isEmpty())
                    {
                        Toast.makeText(Registration.this, "Surname Is Required", Toast.LENGTH_SHORT).show();

                    }
                    else if(Email.isEmpty() )
                    {
                        Toast.makeText(Registration.this, "Email Is Required", Toast.LENGTH_SHORT).show();

                    }else if(!Email.matches(emailPattern)){
                        Toast.makeText(Registration.this, "Invalid email address", Toast.LENGTH_SHORT).show();

                    }
                     else if(  Password.isEmpty())
                    {
                        Toast.makeText(Registration.this, "Password Is Required", Toast.LENGTH_SHORT).show();

                    }else if(Password.length()<5){
                        Toast.makeText(Registration.this, "Password should contain five character or more. Try again", Toast.LENGTH_SHORT).show();
                    }


                    else if(  MobileNo.isEmpty())
                    {
                        Toast.makeText(Registration.this, "Mobile Number are Required", Toast.LENGTH_SHORT).show();

                    }else if(!MobileNo.matches(MobilePattern)){
                        Toast.makeText(Registration.this, "Mobile Number is invalid. Try again", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        DatabaseHelper databaseHelper = new DatabaseHelper(Registration.this);

                        //Check if the email exist on the database before inserting the records
                        boolean checkmail=databaseHelper.checkEmail(Email);
                        if (checkmail==true) {

                            Bank_User bank_user=new Bank_User(Name,Surname,Email,Password,MobileNo,Gender);

                            boolean userDetails = databaseHelper.addUser(bank_user);

                            //Verify if the data has been inserted successfully and whether the account is created
                            if (userDetails == true) {
                                Toast.makeText(Registration.this, "User account creation was successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this, Login.class));
                            } else {
                                Toast.makeText(Registration.this, "Error!!", Toast.LENGTH_SHORT).show();


                            }
                        }else{
                            Toast.makeText(Registration.this, "Account Already exist", Toast.LENGTH_SHORT).show();
                        }
                    }



            }
        });
    }


public void returnLoginActivity(){
    returnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Registration.this, Login.class));
        }
    });
}


}
