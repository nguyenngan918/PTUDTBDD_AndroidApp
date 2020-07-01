package com.app.ngan.quanlidanhba_app.models;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@IgnoreExtraProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String id;
    private String fname;
    private String name;
    private  String password;
    private  String email;
    private String sex;
    private String birthday;
    private String linkAvatar;
    private String work;
    private String relationship;
    private String language;
    private String isOnline;
    private String tokenNotification;
    private String linkBackground;
}
