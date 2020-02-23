package com.xriamer.mixdemo.Util;

/**
 * MixDemo;
 * com.xriamer.mixdemo.Util;
 * Created by Xriam on 12/22/2018;
 */
public class Box {
    private String title;
    private String account;
    private String password;
    private int imageId;

    public Box(String title, String account, String password, int imageId) {
        this.title = title;
        this.account = account;
        this.password = password;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public int getImageId() {
        return imageId;
    }


}
