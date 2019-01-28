package com.pax.mvvmsample.ui.wanandroid.home;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.library.base.BaseFragment;
import com.example.library.base.adpter.BindingViewHolder;
import com.example.library.base.adpter.MyBaseBindingRecyclerViewAdapter;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.component.agentweb.AgentWebActivity;
import com.pax.mvvmsample.databinding.FragmentHomeWanAnroidBinding;
import com.pax.mvvmsample.databinding.FragmentHomeWanandroidItemBinding;
import com.pax.mvvmsample.http.bean.wanAndroid.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeWanAnroidFragment extends BaseFragment<FragmentHomeWanAnroidBinding, HomeWandroidVM> {

    ArrayList<String> mBannerUrls = new ArrayList<>();
    private Banner banner;
    private MyBaseBindingRecyclerViewAdapter<HomeWanAndroidItemVM> adapter;

    public static int[] icons = {R.drawable.ic_boy_one,R.drawable.ic_boy_two,R.drawable.ic_boy_three,R.drawable.ic_boy_four,R.drawable.ic_boy_five,
            R.drawable.ic_girl_one,R.drawable.ic_girl_two,R.drawable.ic_girl_three,R.drawable.ic_girl_four,R.drawable.ic_girl_five};

    Random random = new Random();
    public HomeWanAnroidFragment() {

    }

    public static HomeWanAnroidFragment newInstance() {
        HomeWanAnroidFragment fragment = new HomeWanAnroidFragment();
        return fragment;
    }


    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_wan_anroid;
    }


    @Override
    protected void initViewAndEvent() {
        initView();
        initData();
        mViewModel.loadData();

    }

    private void initData() {
        mViewModel.bannerData.observe(this, new Observer<List<BannerBean>>() {
            @Override
            public void onChanged(@Nullable List<BannerBean> bannerBeans) {
                ArrayList<String> imageUrls = new ArrayList<>();
                ArrayList<String> titles = new ArrayList<>();
                mBannerUrls.clear();
                for (BannerBean bean : bannerBeans) {
                    imageUrls.add(bean.getImagePath());
                    titles.add(bean.getTitle());
                    mBannerUrls.add(bean.getUrl());
                }
               //设置banner样式
                banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(imageUrls);
                //banner设置方法全部调用完毕时最后调用
                //设置banner动画效果
                banner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时） 
                banner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                banner.isAutoPlay(true);
                //设置轮播时间
                banner.setDelayTime(2000);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
            }
        });
    }

    private void initView() {
        setRefreshLsyout(mBinding.refreshLayout);
        initRecycleView();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                AgentWebActivity.loadUrl(getContext(),mBannerUrls.get(position));
            }
        });
    }

    private void initRecycleView() {
        RecyclerView recyclerView = mBinding.recyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyBaseBindingRecyclerViewAdapter<HomeWanAndroidItemVM>(getActivity()) {
            @Override
            protected void convert(BindingViewHolder holder, HomeWanAndroidItemVM item, int position) {
                FragmentHomeWanandroidItemBinding binding = (FragmentHomeWanandroidItemBinding)holder.getBinding();
                binding.iconAndroidHome.setImageResource(icons[random.nextInt(10)]);
            }

            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.fragment_home_wanandroid_item;
            }

            @Override
            protected int getItemVariableId() {
                return BR.item;
            }
        };
        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.head_banner, null));
        banner = mHeaderGroup.findViewById(R.id.head_banner);
        mHeaderGroup.removeView(banner);
        adapter.setHeaderView(banner);
        adapter.setItems(mViewModel.items);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyBaseBindingRecyclerViewAdapter.OnItemClickListener<HomeWanAndroidItemVM>() {
            @Override
            public void onClick(ViewDataBinding binding, int position, HomeWanAndroidItemVM item) {
                AgentWebActivity.loadUrl(getContext(),item.mUrl);
            }
        });



    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mViewModel.loadData();
    }

    public class GlideImageLoader extends ImageLoader {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.img_one_bi_one).
                error(R.mipmap.img_one_bi_one);
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .apply(requestOptions)
                    .load((String) path)
                    .into(imageView);
        }

    }


}
