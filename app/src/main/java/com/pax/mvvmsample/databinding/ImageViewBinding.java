package com.pax.mvvmsample.databinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public final class ImageViewBinding {
    @BindingAdapter(value = {"binding_imageUrl"})
    public static void setImageUri(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

}
