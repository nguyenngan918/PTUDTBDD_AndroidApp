package com.app.ngan.quanlidanhba_app.models;

public class ItemMenu {
    private int icon;
    private int title;

    public ItemMenu(int icon, int title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}

