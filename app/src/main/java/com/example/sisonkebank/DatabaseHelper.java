package com.example.sisonkebank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Declaring variables
    public static final String Database_Name = "Bank.db";
    public static final String Table_Name = "Users";
    public static final String col_1 = "First_Name";
    public static final String col_2 = "Surname";
    public static final String col_3 = "Email";
    public static final String col_4 = "Password";
    public static final String col_5 = "Mobile_Number";
    public static final String col_6 = "Gender";
    public static final String col_7 = "Savings_Balance";
    public static final String col_8 = "Current_Balance";
    //initializing the values




    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CreateTable = "CREATE TABLE Users" + "( First_Name TEXT(50) ,Surname TEXT(50), Email VARCHAR(255) PRIMARY KEY, Password TEXT, Mobile_Number TEXT, Gender BOOL,Savings_Balance Double,Current_Balance Double)";
        db.execSQL(CreateTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS STUDENTS");
        // onCreate(db);

    }

    // register user
    public boolean addUser(Bank_User bank_user) {

        SQLiteDatabase db = DatabaseHelper.this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(col_1, bank_user.getFirst_Name());
        cv.put(col_2, bank_user.getSurname());
        cv.put(col_3, bank_user.getEmail());
        cv.put(col_4, bank_user.getPassword());
        cv.put(col_5, bank_user.getMobile_Number());
        cv.put(col_6, bank_user.isGender());
        cv.put(col_7,bank_user.getSavings_Balance());
        cv.put(col_8,bank_user.getCurrent_Balance());
        long insert = db.insert(Table_Name, null, cv);

        if (insert == -1) {
            return false;

        } else {
            return true;
        }

    }

    //login Authentication
    public boolean loginAuthentication(String email, String password) {

        SQLiteDatabase db = DatabaseHelper.this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where Email=? and Password=?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;

        } else {
            return false;
        }

    }

    //get user details
    public Cursor getUserDetails(String Email) {

        SQLiteDatabase db = DatabaseHelper.this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where Email=?", new String[]{Email});

        return cursor;


    }

    //update user balance
    public boolean updateBalance(Double NewCurrentBalance,Double NewSavingsBalance ,String Email)
    {
        SQLiteDatabase db = DatabaseHelper.this.getReadableDatabase();

        Bank_User bank_user=new Bank_User();

        ContentValues cv = new ContentValues();

        cv.put("Current_Balance",NewCurrentBalance);
        cv.put("Savings_Balance",NewSavingsBalance);

        int update = db.update(Table_Name, cv, "Email = ?", new String[]{Email});
        if(update==1){
            return true;

        }else{
            return false;
        }

    }

    //check if the email exists
    public boolean checkEmail(String Email) {
        SQLiteDatabase db = DatabaseHelper.this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Users where Email=?", new String[]{Email});
        if (cursor.getCount() > 0) {

            return false;
        } else {
            return true;
        }
    }

}

