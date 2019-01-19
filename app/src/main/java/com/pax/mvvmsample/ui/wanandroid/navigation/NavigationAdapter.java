package com.pax.mvvmsample.ui.wanandroid.navigation;

import android.content.Context;
import android.databinding.ObservableList;
import android.view.View;

import com.example.library.base.adpter.BindingViewHolder;
import com.example.library.base.adpter.MyBaseBindingRecyclerViewAdapter;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.databinding.FragmentNaviItemBinding;

public class NavigationAdapter extends MyBaseBindingRecyclerViewAdapter<NaviItemViewModel> {

    public NavigationAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(BindingViewHolder holder, final NaviItemViewModel item, final int position) {
//        final FragmentNaviItemBinding binding = (FragmentNaviItemBinding)holder.getBinding();
//        binding.getRoot().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ObservableList<NaviItemViewModel> items = getItems();
//                for(NaviItemViewModel naviItemViewModel:items){
//                    naviItemViewModel.setSelected(false);
//                }
//                item.setSelected(true);
//                binding.tvChapterName.setSelected(true);
//                notifyDataSetChanged();
//            }
//        });

    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.fragment_navi_item;
    }

    @Override
    protected int getItemVariableId() {
        return BR.item;
    }
}
