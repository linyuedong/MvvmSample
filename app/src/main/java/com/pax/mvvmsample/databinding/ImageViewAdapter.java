package com.pax.mvvmsample.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageViewAdapter {

    @BindingAdapter(value = {"imageUrl"})
    public static void setImageUrl(ImageView view,String imageUrl){
        Glide.with(view.getContext()).load(imageUrl).into(view);

    }
}
