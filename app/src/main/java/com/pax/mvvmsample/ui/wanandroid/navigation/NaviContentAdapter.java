package com.pax.mvvmsample.ui.wanandroid.navigation;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.base.adpter.BindingViewHolder;
import com.example.library.base.adpter.MyBaseBindingRecyclerViewAdapter;
import com.example.library.view.webView.WebViewActivity;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.component.agentweb.AgentWebActivity;
import com.pax.mvvmsample.databinding.FragmentNaviContentItemBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Random;

public class NaviContentAdapter extends MyBaseBindingRecyclerViewAdapter<NaviItemViewModel> {

    public NaviContentAdapter(Context context) {
        super(context);
    }
    @Override
    protected void convert(BindingViewHolder holder, NaviItemViewModel item, int position) {
        FragmentNaviContentItemBinding binding = (FragmentNaviContentItemBinding)holder.getBinding();
        final ArrayList<String> secongTitles = item.getTitles();
        final ArrayList<String> urls = item.getUrls();
        final TagFlowLayout mFlowLayout = binding.idFlowlayout;
        mFlowLayout.setAdapter(new TagAdapter<String>(secongTitles) {
            @Override
            public View getView(FlowLayout parent, int position, String data ) {
                Button tv = (Button) mLayoutInflater.inflate(R.layout.flowlayout_item,
                        mFlowLayout, false);
                tv.setText(data);
                tv.setTextColor(mContext.getResources().getColor(getTextColor()));
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                AgentWebActivity.loadUrl(mContext,urls.get(position),secongTitles.get(position));
                return true;
            }
        });
    }


    private int getTextColor() {
        return colorArray[random.nextInt(10)];
    }


    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.fragment_navi_content_item;
    }

    @Override
    protected int getItemVariableId() {
        return BR.item;
    }

    public static int[] colorArray = {R.color.dimgray,R.color.orange,R.color.deeppink,R.color.black,
            R.color.lightseagreen ,R.color.mediumslateblue,R.color.darkred,R.color.chocolate,R.color.colorRateRed,
            R.color.dodgerblue,R.color.forestgreen};

    Random random = new Random();
}
