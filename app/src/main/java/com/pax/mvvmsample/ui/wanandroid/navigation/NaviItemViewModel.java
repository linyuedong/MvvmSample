package com.pax.mvvmsample.ui.wanandroid.navigation;

import java.util.ArrayList;

public class NaviItemViewModel {

    public String chapterName;

    public ArrayList<String> titles = new ArrayList<>();
    public ArrayList<String> urls = new ArrayList<>();

    private  boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }
}
