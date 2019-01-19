package com.pax.mvvmsample.ui.wanandroid.navigation;

import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.library.Utils.LogUtlis;
import com.example.library.base.BaseFragment;
import com.example.library.base.adpter.MyBaseBindingRecyclerViewAdapter;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentNaviItemBinding;
import com.pax.mvvmsample.databinding.FragmentNavigationBinding;


public class NavigationFragment extends BaseFragment<FragmentNavigationBinding, NavigationViewModel> {


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
        showContentView();

        RecyclerView rvNavi = mBinding.rvNavi;
        RecyclerView rvNaviContent = mBinding.rvNaviContent;

        LinearLayoutManager naviLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNavi.setLayoutManager(naviLayoutManager);
        final NavigationAdapter navigationAdapter = new NavigationAdapter(getContext());
        rvNavi.setAdapter(navigationAdapter);
        navigationAdapter.setItems(mViewModel.naviItemViewModels);

        LinearLayoutManager contentLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNaviContent.setLayoutManager(contentLayoutManager);
        NaviContentAdapter naviContentAdapter = new NaviContentAdapter(getContext());
        rvNaviContent.setAdapter(naviContentAdapter);
        naviContentAdapter.setItems(mViewModel.naviItemViewModels);


        navigationAdapter.setOnItemClickListener(new MyBaseBindingRecyclerViewAdapter.OnItemClickListener<NaviItemViewModel>() {
            @Override
            public void onClick(ViewDataBinding binding, int position, NaviItemViewModel item) {
                FragmentNaviItemBinding binding1 = (FragmentNaviItemBinding) binding;
                ObservableList<NaviItemViewModel> items = navigationAdapter.getItems();
                for(NaviItemViewModel naviItemViewModel:items){
                    naviItemViewModel.setSelected(false);
                }
                item.setSelected(true);
                binding1.tvChapterName.setSelected(true);
                navigationAdapter.notifyDataSetChanged();
                LogUtlis.i("position = " + binding1.tvChapterName.isSelected());
                LogUtlis.i("position = " + item.isSelected());
                LogUtlis.i("position = " + position);
                Toast.makeText(getContext(),item.getChapterName(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void initData() {
        mViewModel.loadData();

    }

}
