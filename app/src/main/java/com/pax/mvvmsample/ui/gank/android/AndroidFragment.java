package com.pax.mvvmsample.ui.gank.android;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentAndroidBinding;
import com.pax.mvvmsample.BR;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


public class AndroidFragment extends BaseFragment<FragmentAndroidBinding, AndroidViewModel> {


    public AndroidFragment() {
    }

    public static AndroidFragment newInstance() {
        return new AndroidFragment();
    }


    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initViewAndEvent() {
        initView();
        mViewModel.loadAndroidData();
    }

    private void initView() {
        final SmartRefreshLayout refreshLayout = mBinding.refreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        mViewModel.status.observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                if (throwable != null) {
                    Snackbar.make(mRootView, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mViewModel.loadMoreState.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean successs) {
                refreshLayout.finishLoadMore(successs);
            }
        });

        mViewModel.refreshState.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean success) {
                refreshLayout.finishRefresh(success);
            }
        });

    }
}
