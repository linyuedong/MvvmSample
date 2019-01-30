package com.example.library.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.library.Utils.CommonUtils;
import com.example.library.databinding.ActivityBaseBinding;
import com.example.library.R;
import com.example.library.view.statusBar.StatusBarUtil;
import com.noober.background.BackgroundLibrary;

import java.lang.reflect.ParameterizedType;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected V mBinding;
    protected VM mViewModel;
    private ActivityBaseBinding mBaseBinding;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initRootView();
        initDataBinding();
        initViewAndData();
    }

    private void initRootView() {
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutID(),null,false);
        mToolbar = mBaseBinding.toolBar;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(mBinding.getRoot(),params);
        setToolBar();
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorPrimaryDark), 0);

    }

    private void setToolBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            //去除默认Title显示
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    onBackPressed();
                }
            }
        });

    }

    public void setCenterTitle(CharSequence title) {
        mToolbar.setTitle(title);
        for (int i = 0; i < mToolbar.getChildCount(); i++) {
            View view = mToolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (title.equals(textView.getText())) {
                    textView.setGravity(Gravity.CENTER);
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    textView.setLayoutParams(params);
                }
            }

        }
    }

    protected void displayHomeAsUpEnabled(boolean displayHomeAsUpEnabled){
        if(mActionBar != null){
            mActionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        }

    }

    private void initDataBinding() {
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
