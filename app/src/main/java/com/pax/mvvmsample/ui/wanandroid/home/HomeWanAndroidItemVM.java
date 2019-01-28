package com.pax.mvvmsample.ui.wanandroid.home;


public class HomeWanAndroidItemVM {


    public String mTitle;
    public String mAuthor;
    public String mChapterName;
    public String mSuperChapterName;
    public String mNiceDate;
    public String mUrl;


    public HomeWanAndroidItemVM(String title, String author, String chapterName, String superChapterName, String niceDate, String link) {
        mTitle = title;
        mAuthor = author;
        mChapterName = chapterName;
        mSuperChapterName = superChapterName;
        mNiceDate = niceDate;
        mUrl = link;
    }


}
