package com.mub.wease.wease.Data;

/**
 * Created by Andymub on 08/02/2018.
 */

public class Upload {

    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)

    public Upload() {
    }

    public Upload(String fullName, String url) {
        this.name = fullName;
        this.url = url;
    }
    public Upload(String fullName)
    {
        this.name = fullName;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}