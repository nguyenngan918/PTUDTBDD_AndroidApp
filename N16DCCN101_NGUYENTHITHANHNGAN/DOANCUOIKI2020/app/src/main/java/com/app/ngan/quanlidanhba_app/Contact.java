package com.app.ngan.quanlidanhba_app;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@IgnoreExtraProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable {
    private String key;
    private  String lastName;
    private String name;
    private String Company;
    private String phone;
    private String Access;
    private String gmail;
    private String Facebook;
    private String url ;
    private String image;
    private int sttCall;
    private int sttBox;
    private int block;

    public Contact(String name, String phone, String image, int sttCall) {
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.sttCall = sttCall;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
