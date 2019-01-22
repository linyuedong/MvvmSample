package com.pax.mvvmsample.ui.wanandroid.navigation;

import android.content.Context;
import android.databinding.ObservableList;
import android.view.View;

import com.example.library.base.adpter.BindingViewHolder;
import com.example.library.base.adpter.MyBaseBindingRecyclerViewAdapter;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentNaviItemBinding;

public class NavigationAdapter extends MyBaseBindingRecyclerViewAdapter<NaviItemViewModel> {

    public NavigationAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(BindingViewHolder holder, final NaviItemViewModel item, final int position) {
        final FragmentNaviItemBinding binding = (FragmentNaviItemBinding) holder.getBinding();
        binding.tvChapterName.setSelected(item.isSelected());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectItem(position);
                if (listener != null) {
                    listener.onSelected(position);
                }
            }
        });


    }

    public void selectItem(int position) {
        final ObservableList<NaviItemViewModel> data = getItems();
        for (int i = 0; i < data.size(); i++) {
            if (i == position) {
                data.get(i).setSelected(true);
            } else {
                data.get(i).setSelected(false);
            }

        }

        notifyDataSetChanged();
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.fragment_navi_item;
    }

    @Override
    protected int getItemVariableId() {
        return BR.item;
    }

    private OnSelectListener listener;

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onSelected(int position);
    }
}
