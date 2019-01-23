package com.pax.mvvmsample.ui.wanandroid.navigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentNavigationBinding;


public class NavigationFragment extends BaseFragment<FragmentNavigationBinding, NavigationViewModel> {


    private int oldPosition = 0;

    public NavigationFragment() {
        // Required empty public constructor
    }


    public static NavigationFragment newInstance() {
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }

    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initViewAndEvent() {
        initview();
        initData();

    }

    private void initview() {
        final RecyclerView rvNavi = mBinding.rvNavi;
        RecyclerView rvNaviContent = mBinding.rvNaviContent;

        LinearLayoutManager naviLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNavi.setLayoutManager(naviLayoutManager);
        final NavigationAdapter navigationAdapter = new NavigationAdapter(getContext());
        rvNavi.setAdapter(navigationAdapter);
        navigationAdapter.setItems(mViewModel.naviItemViewModels);


        final LinearLayoutManager contentLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNaviContent.setLayoutManager(contentLayoutManager);
        NaviContentAdapter naviContentAdapter = new NaviContentAdapter(getContext());
        rvNaviContent.setAdapter(naviContentAdapter);
        naviContentAdapter.setItems(mViewModel.naviItemViewModels);


        navigationAdapter.setOnSelectListener(new NavigationAdapter.OnSelectListener() {
            @Override
            public void onSelected(int position) {
                contentLayoutManager.scrollToPositionWithOffset(position, 0);
            }
        });

        rvNaviContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentPosition = contentLayoutManager.findFirstVisibleItemPosition();
                if(currentPosition != oldPosition){
                    navigationAdapter.selectItem(currentPosition);
                    rvNavi.scrollToPosition(currentPosition);
                    oldPosition = currentPosition;
                }
            }
        });



    }

    private void initData() {
        mViewModel.loadData();
    }

}
