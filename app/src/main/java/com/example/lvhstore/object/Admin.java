package com.example.lvhstore.object;

public class Admin {
    private String tkadmin;
    private String mkadmin;

    public Admin(String tkadmin, String mkadmin) {
        this.tkadmin = tkadmin;
        this.mkadmin = mkadmin;
    }

    public String getTkadmin() {
        return tkadmin;
    }

    public void setTkadmin(String tkadmin) {
        this.tkadmin = tkadmin;
    }

    public String getMkadmin() {
        return mkadmin;
    }

    public void setMkadmin(String mkadmin) {
        this.mkadmin = mkadmin;
    }
}
