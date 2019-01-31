package com.pax.mvvmsample.ui.wanandroid.home;


import com.example.library.Utils.LogUtlis;
import com.example.library.binding.command.BindAction0;
import com.pax.mvvmsample.app.MyApplication;
import com.pax.mvvmsample.component.agentweb.AgentWebActivity;

public class HomeWanAndroidItemVM {


    public String mTitle;
    public String mAuthor;
    public String mChapterName;
    public String mSuperChapterName;
    public String mNiceDate;
    public String mUrl;

    private  boolean isCollection;

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public  BindAction0 cardViewOnClick = new BindAction0() {
        @Override
        public void call() {
            LogUtlis.i("cardViewOnClick");
            AgentWebActivity.loadUrl(MyApplication.getContext(),mUrl);
        }
    };

    public  BindAction0 collectionOnClick = new BindAction0() {
        @Override
        public void call() {
            LogUtlis.i("collectionOnClick");


        }
    };

    public HomeWanAndroidItemVM(String title, String author, String chapterName, String superChapterName, String niceDate, String link) {
        mTitle = title;
        mAuthor = author;
        mChapterName = chapterName;
        mSuperChapterName = superChapterName;
        mNiceDate = niceDate;
        mUrl = link;


    }



}
