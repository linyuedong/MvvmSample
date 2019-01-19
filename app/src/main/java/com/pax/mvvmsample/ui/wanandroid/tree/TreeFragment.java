package com.pax.mvvmsample.ui.wanandroid.tree;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentTreeBinding;


public class TreeFragment extends BaseFragment<FragmentTreeBinding,TreeViewModel> {



    public TreeFragment() {
    }

    public static TreeFragment newInstance() {
        TreeFragment fragment = new TreeFragment();
        return fragment;
    }


    @Override
    protected void initViewAndEvent() {
        showContentView();
        RecyclerView recyclerView = mBinding.recyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        TreeFragmentAdapter treeAdapter = new TreeFragmentAdapter(getContext());
        recyclerView.setAdapter(treeAdapter);
        treeAdapter.setItems(mViewModel.treeItemViewModels);

        mViewModel.loadData();

    }



    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tree;
    }
}
