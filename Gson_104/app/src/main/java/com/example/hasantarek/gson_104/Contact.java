package com.example.hasantarek.gson_104;

/**
 * Created by HASAN TAREK on 1/22/2018.
 */
public class Contact {

    private String name,email,profile_pic,response;

    public Contact(String name, String email, String profile_pic) {
        this.name = name;
        this.email = email;
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getResponse() {
        return response;
    }
}
