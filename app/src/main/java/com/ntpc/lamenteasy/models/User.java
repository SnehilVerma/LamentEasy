package com.ntpc.lamenteasy.models;

/**
 * Created by Snehil Verma on 7/7/2016.
 */
public class User {


    public String email;
    public String profile;
    public String dept;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email,String profile) {

        this.email = email;
        this.profile = profile;
        //this.dept=dept;
    }


    public String getProfile(){
        return profile;
    }
}
