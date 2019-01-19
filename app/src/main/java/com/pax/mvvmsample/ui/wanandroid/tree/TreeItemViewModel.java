package com.pax.mvvmsample.ui.wanandroid.tree;

import android.databinding.ObservableField;

import java.util.ArrayList;

public class TreeItemViewModel {




    public String firstTitle;
    public ArrayList<String> secongTitles = new ArrayList<>();


    public String getFirstTitle() {
        return firstTitle;
    }

    public void setFirstTitle(String firstTitle) {
        this.firstTitle = firstTitle;
    }

    public ArrayList<String> getSecongTitles() {
        return secongTitles;
    }

    public void setSecongTitles(ArrayList<String> secongTitles) {
        this.secongTitles = secongTitles;
    }
}
