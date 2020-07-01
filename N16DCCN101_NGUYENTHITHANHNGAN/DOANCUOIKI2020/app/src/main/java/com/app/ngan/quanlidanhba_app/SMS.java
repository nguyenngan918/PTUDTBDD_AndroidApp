package com.app.ngan.quanlidanhba_app;

public class SMS {
    private String nameSMS;
    private String cpnSMS;

    public String getCpnSMS() {
        return cpnSMS;
    }

    public void setCpnSMS(String cpnSMS) {
        this.cpnSMS = cpnSMS;
    }

    public SMS(String nameSMS, String cpnSMS) {
        this.nameSMS = nameSMS;
        this.cpnSMS = cpnSMS;
    }

    public String getNameSMS() {
        return nameSMS;
    }

    public void setNameSMS(String nameSMS) {
        this.nameSMS = nameSMS;
    }
}
