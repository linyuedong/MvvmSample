package com.pax.mvvmsample.databinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pax.mvvmsample.R;

public final class ImageViewBinding {
    @BindingAdapter(value = {"binding_imageUrl"})
    public static void setImageUri(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.img_one_bi_one).
                    error(R.mipmap.img_one_bi_one);
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .apply(requestOptions)
                    .load(url)
                    .into(imageView);
        }
    }

}
