package com.pax.mvvmsample.ui.gank.beauty.bigphoto;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class BigPhotoPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mData;
    private int mFirstPosition;
    public BigPhotoPagerAdapter(Context context ,List<String> list,int firstPosition) {
        mContext = context;
        mData = list;
        mFirstPosition = firstPosition;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        Glide.with(mContext).load(mData.get(position)).into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

}
