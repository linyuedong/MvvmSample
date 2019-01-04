package com.example.library.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.ParameterizedType;


public abstract class BaseFragment<V extends ViewDataBinding , VM extends BaseViewModel> extends Fragment {


    protected V mBinding;
    protected View mRootView;
    protected VM mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDataBinding(inflater,container);
        return mRootView = mBinding.getRoot();
    }

    private void initDataBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        Class<VM> clazz = (Class <VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        mViewModel = (VM) ViewModelProviders.of(getActivity()).get(clazz);
        getLifecycle().addObserver(mViewModel);
        mBinding.setVariable(initBR(), mViewModel);
    }

    protected abstract int initBR();


    public abstract int getLayoutId();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.unbind();
        getLifecycle().removeObserver(mViewModel);
        mViewModel = null;
    }
}
