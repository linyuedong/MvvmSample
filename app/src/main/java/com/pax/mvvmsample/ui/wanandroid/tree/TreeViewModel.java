package com.pax.mvvmsample.ui.wanandroid.tree;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.library.Utils.RxUtils;
import com.example.library.base.BaseViewModel;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.wanAndroid.TreeBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TreeViewModel extends BaseViewModel {

    public TreeViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<TreeItemViewModel> treeItemViewModels = new ObservableArrayList<TreeItemViewModel>();



    public void loadData() {
        ApiHelper.getWanAndroidApis().getTree()
                .compose(RxUtils.<WanAndroidResponse<List<TreeBean>>>rxSchedulersHelper())
                .compose(RxUtils.<WanAndroidResponse<List<TreeBean>>>rxErrorHelper())
                .as(RxUtils.<WanAndroidResponse<List<TreeBean>>>bindLifecycle(getLifeCycle()))
                .subscribe(new Observer<WanAndroidResponse<List<TreeBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidResponse<List<TreeBean>> response) {
                        List<TreeBean> data = response.getData();
                        if(data != null || data.size() > 0){
                            for (int i=0;i<data.size();i++){
                                TreeItemViewModel treeItemViewModel = new TreeItemViewModel();
                                TreeBean treeBean = data.get(i);
                                String firstName = treeBean.getName();
                                treeItemViewModel.setFirstTitle(firstName);
                                List<TreeBean.ChildrenBean> children = treeBean.getChildren();
                                if(children !=null&&children.size()>0){
                                    for(int j=0;j<children.size();j++){
                                        String secondTitle = children.get(j).getName();
                                        treeItemViewModel.getSecongTitles().add(secondTitle);
                                    }
                                }
                                treeItemViewModels.add(treeItemViewModel);
                            }


                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
