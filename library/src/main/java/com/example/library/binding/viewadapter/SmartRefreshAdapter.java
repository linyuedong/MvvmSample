package com.example.library.binding.viewadapter;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;

import com.example.library.binding.command.BindAction0;
import com.example.library.binding.command.BindAction1;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

public class SmartRefreshAdapter {
    @BindingAdapter(value = {"bind_onRerfeshCommand","bind_loadMoreCommand"},requireAll = false)
    public static void setCommand(SmartRefreshLayout view, final BindAction0 onRefreshCommad, final BindAction0 loadMoreCommand){
        view.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreCommand.call();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onRefreshCommad.call();
            }
        });
    }
}
