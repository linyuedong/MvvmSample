package com.pax.mvvmsample.Utils;
import android.arch.lifecycle.LifecycleOwner;
import android.widget.Toast;
import com.example.library.Utils.LogUtlis;
import com.example.library.http.exception.ExceptionHandle;
import com.pax.mvvmsample.app.MyApplication;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    private static final int STATUS_UNAUTHORIZED = 401;


    public static <T> ObservableTransformer<T,T> rxSchedulersHelper(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> ObservableTransformer<T,T> rxErrorHelper(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                        return Observable.error(ExceptionHandle.handleException(throwable));
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //Toast.makeText(MyApplication.getContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }


    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner)
        );
    }









}
