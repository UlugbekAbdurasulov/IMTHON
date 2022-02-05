package com.example.imthon;

import java.io.Serializable;

public class Contact implements Serializable {

    public int profile;
    public String name;
    public String phoneNumber;

    public Contact(int profile, String name, String phoneNumber) {
        this.profile = profile;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public Contact(){

    }


    @Override
    public String toString() {
        return "Contact{" +
                "profile=" + profile +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}