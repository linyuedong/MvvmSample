package com.pax.mvvmsample.ui.gank.beauty;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.library.base.BaseFragment;
import com.example.library.base.adpter.BaseRecycleViewAdapter;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentBeautyBinding;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.ui.gank.beauty.bigphoto.BigPhotoActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

public class BeautyFragment extends BaseFragment<FragmentBeautyBinding,BeautyViewModel> {

    private SmartRefreshLayout refreshLayout;

    public BeautyFragment() {

    }

    public static BeautyFragment newInstance(){
        return new BeautyFragment();
    }

    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void initViewAndEvent() {
        initView();
        initEvent();
        mViewModel.loadBeautyData();
    }

    private void initEvent() {
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

    private void initView() {
        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        refreshLayout = mBinding.refreshLayout;
        setRefreshLsyout(refreshLayout);
        mViewModel.adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener<BeautyItemViewModel>() {
            @Override
            public void onClick(View v, int position, BeautyItemViewModel item) {
                ObservableList<BeautyItemViewModel> items = mViewModel.items;
                ArrayList<String> urls = new ArrayList<>();
                for(int i = 0;i<items.size();i++){
                    urls.add(items.get(i).mUrl);
                }

                Intent intent = new Intent(getContext(),BigPhotoActivity.class);
                intent.putExtra("position",position);
                intent.putStringArrayListExtra("urls",urls);
                startActivity(intent);
            }
        });

    }


}
