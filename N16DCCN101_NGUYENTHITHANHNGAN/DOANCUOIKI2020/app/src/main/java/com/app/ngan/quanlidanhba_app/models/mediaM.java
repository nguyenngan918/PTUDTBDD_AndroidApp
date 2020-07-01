package com.app.ngan.quanlidanhba_app.models;

public class mediaM {
    private String mNameImage;
    private String mImageUrl;

    public mediaM() {

    }

    public mediaM(String mNameImage, String mImageUrl) {
        this.mNameImage = mNameImage;
        this.mImageUrl = mImageUrl;
    }

    public String getmNameImage() {
        return mNameImage;
    }

    public void setmNameImage(String mNameImage) {
        this.mNameImage = mNameImage;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
