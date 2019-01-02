package com.pax.mvvmsample.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;


public class GankFragment extends Fragment {


    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentGankBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gank, container, false);

        ImageBean imageBean = new ImageBean();
        binding.setVariable(BR.vm,imageBean);

        imageBean.url.set("http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg");

        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
