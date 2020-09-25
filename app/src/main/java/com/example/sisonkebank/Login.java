package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button login;
    TextView createAcc;
    EditText email,password;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login= findViewById(R.id.btnlogin);
        createAcc=findViewById(R.id.tvCreateAccount);
        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);
        databaseHelper=new DatabaseHelper(Login.this);


        loginAuth();
        creatAccount();

    }

    public void loginAuth()

    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email=email.getText().toString().trim();
                String Password=password.getText().toString().trim();

                // verify the email and password
                Boolean checkloginDetails=databaseHelper.loginAuthentication(Email,Password);

                //validation
                if(Email.isEmpty()) {

                    Toast.makeText(Login.this, "Email is required!!", Toast.LENGTH_SHORT).show();

                }else if( Password.isEmpty()){

                    Toast.makeText(Login.this, "Password is required!!", Toast.LENGTH_SHORT).show();
                }

                // if the Email  and password match

                else if(checkloginDetails==true)
                {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,MainPage.class);

                    //passing the email using Intent
                    intent.putExtra("Email",Email);
                    startActivity(intent);
                }
                else
                    {

                    Toast.makeText(Login.this, "Incorrect Login details either EMAIL OR PASSWORD!!", Toast.LENGTH_SHORT).show();

                    }
            }
        });
    }


    public void creatAccount(){

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
    }
}

