package com.example.library.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.ParameterizedType;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected V mBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initDataBinding();
        initViewAndData();
    }


    private void initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutID());
        Class<VM> clazz = (Class <VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        mViewModel = (VM) ViewModelProviders.of(this).get(clazz);
        mBinding.setVariable(initBR(), mViewModel);
        getLifecycle().addObserver(mViewModel);
        mViewModel.addLifeCycle(this);
    }

    protected abstract int initBR();


    public abstract int getLayoutID() ;

    protected abstract void initViewAndData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        mBinding.unbind();
        getLifecycle().removeObserver(mViewModel);
        mViewModel = null;
    }
}
