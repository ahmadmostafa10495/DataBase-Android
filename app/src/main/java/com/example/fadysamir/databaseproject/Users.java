package com.example.fadysamir.databaseproject;

/**
 * Created by Fady Samir on 2/9/2018.
 */

public class Users {

    private String mail, name;

    public Users(){

    }
    public Users(String name, String mail){
        this.mail = mail;
        this.name = name;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public String getMail (){
        return mail;
    }

    public String getName (){
        return name;
    }


}
