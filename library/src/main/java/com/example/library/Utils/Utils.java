package com.example.library.Utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Utils {

    @NonNull
    public static <T> T checkNotNull(@Nullable final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }
}
