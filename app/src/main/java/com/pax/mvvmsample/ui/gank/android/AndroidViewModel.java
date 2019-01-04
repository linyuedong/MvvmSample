package com.pax.mvvmsample.ui.gank.android;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.library.base.BaseViewModel;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AndroidViewModel extends BaseViewModel {

    public final ObservableField<String> test = new ObservableField<>();
    public final ObservableList<String> items = new ObservableArrayList<>();
    public final ItemBinding<String> itemBinding = ItemBinding.of(BR.item, R.layout.fragment_gank_item);

    public AndroidViewModel(@NonNull Application application) {
        super(application);
    }


}
