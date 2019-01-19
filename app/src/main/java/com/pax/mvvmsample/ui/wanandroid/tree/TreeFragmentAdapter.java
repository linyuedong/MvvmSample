package com.pax.mvvmsample.ui.wanandroid.tree;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.library.base.adpter.BindingViewHolder;
import com.example.library.base.adpter.MyBaseBindingRecyclerViewAdapter;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentTreeItemBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;


class TreeFragmentAdapter extends MyBaseBindingRecyclerViewAdapter<TreeItemViewModel> {

    public TreeFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(BindingViewHolder holder, TreeItemViewModel item, int position) {
        FragmentTreeItemBinding binding = (FragmentTreeItemBinding)holder.getBinding();
        final ArrayList<String> secongTitles = item.getSecongTitles();
        final TagFlowLayout mFlowLayout = binding.idFlowlayout;
        mFlowLayout.setAdapter(new TagAdapter<String>(secongTitles) {
            @Override
            public View getView(FlowLayout parent, int position, String data ) {
                Button tv = (Button) mLayoutInflater.inflate(R.layout.flowlayout_item,
                        mFlowLayout, false);
                tv.setText(data);
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                Toast.makeText(mContext, secongTitles.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.fragment_tree_item;
    }

    @Override
    protected int getItemVariableId() {
        return BR.item;
    }
}
