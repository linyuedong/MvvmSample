package com.pax.mvvmsample;


import com.example.library.Utils.LogUtlis;


import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class Test {

    public static void test() {
        //test1();
        //test4();
    }


    public static void test1(){
        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtlis.i("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                LogUtlis.i("onNext" + integer);
            }

            @Override
            public void onError(Throwable e) {
                LogUtlis.i("onError");
            }

            @Override
            public void onComplete() {
                LogUtlis.i("onComplete");
            }
        });




    }


    public static void test2(){
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
            LogUtlis.i("Subscribe thread is " + Thread.currentThread().getName());
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtlis.i( "Observer thread is :" + Thread.currentThread().getName());
                LogUtlis.i( "onNext: " + integer);
            }
        };


        observable.subscribe(consumer);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);




    }


    public static void test3(){
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    if (i == 3) {
                        emitter.onError(new ConnectException("ERROR1"));
                    } else {
                        emitter.onNext(i);
                    }
                    Thread.sleep(100);
                }
                emitter.onComplete();
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtlis.i("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                LogUtlis.i("onNext : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                LogUtlis.i("onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtlis.i("onComplete");
            }
        };


//        observable.onErrorResumeNext(new Function<Throwable, ObservableSource>() {
//            @Override
//            public ObservableSource apply(Throwable error) throws Exception {
//                if (error instanceof ConnectException) {
//                    return Observable.error(new ConnectException("ERROR2"));
//                }
//                return Observable.error(error);
//
//            }
//        }).subscribe(observer);
//
//        observable.onErrorResumeNext(new Function<Throwable, ObservableSource>() {
//            @Override
//            public ObservableSource apply(Throwable error) throws Exception {
//                return Observable.just(6,7);
//            }
//        }).subscribe(observer);


    }


    public static void test4(){
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    if (i == 3) {
                        emitter.onError(new ConnectException("ERROR1"));
                    } else {
                        emitter.onNext(i);
                    }
                    Thread.sleep(100);
                }
                emitter.onComplete();
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtlis.i("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                LogUtlis.i("onNext : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                LogUtlis.i("onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtlis.i("onComplete");
            }
        };


        observable
                .retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
                    @Override public Observable<?> apply(Observable<? extends Throwable> errors) {

                        return errors.flatMap(new Function<Throwable, Observable<?>>() {
                            @Override public Observable<?> apply(Throwable error) {

                                // For IOExceptions, we  retry
                                if (error instanceof ConnectException) {
                                    LogUtlis.i("ConnectException");
                                    return Observable.just(null);
                                }

                                // For anything else, don't retry
                                return Observable.error(error);
                            }
                        });
                    }
                })
                .subscribe(observer);


        observable.retryWhen(new RetryWithDelay(3,3000))

                .subscribe(observer);




    }








    static class RetryWithDelay implements
            Function<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public Observable<?> apply(Observable<? extends Throwable> attempts) {
            return attempts
                    .flatMap(new Function<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> apply(Throwable throwable) {
                            if (++retryCount <= maxRetries) {
                                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                                LogUtlis.i( "get error, it will try after " + retryDelayMillis
                                        + " millisecond, retry count " + retryCount);
                                return Observable.timer(retryDelayMillis,
                                        TimeUnit.MILLISECONDS);
                            }
                            // Max retries hit. Just pass the error along.
                            return Observable.error(throwable);
                        }
                    });
        }
    }


}
