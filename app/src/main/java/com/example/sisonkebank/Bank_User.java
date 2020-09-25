package com.example.sisonkebank;


import java.io.Serializable;

public class Bank_User  {

    //Declaring Variables
    String First_Name,Surname,Email,Password,Mobile_Number,Gender;
   public  String NewBalanceSavings;
    public String NewBalanceCurrent;

    //constructor
    public Bank_User(String first_Name, String surname, String email, String password, String mobile_Number, String gender) {
        First_Name = first_Name;
        Surname = surname;
        Email = email;
        Password = password;
        Mobile_Number = mobile_Number;
        Gender = gender;
    }

    //default constructor
    public Bank_User() {
    }

    // convert all to strings
    @Override
    public String toString() {
        return "Bank_User{" +
                "First_Name='" + First_Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Mobile_Number='" + Mobile_Number + '\'' +
                ", Gender=" + Gender +
                '}';
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String isGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }



    //------------------------------------------is used to pass the value to be transferred


    public String getNewBalanceSavings() {
        return NewBalanceSavings;
    }

    public void setNewBalanceSavings(String newBalanceSavings) {
        NewBalanceSavings = newBalanceSavings;
    }

    public String getNewBalanceCurrent() {
        return NewBalanceCurrent;
    }

    public void setNewBalanceCurrent(String newBalanceCurrent) {
        NewBalanceCurrent = newBalanceCurrent;
    }
}


