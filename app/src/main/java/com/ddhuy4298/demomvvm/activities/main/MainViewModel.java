package com.ddhuy4298.demomvvm.activities.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.t3h.basemodule.base.BaseViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isStarted = new MutableLiveData<>();
    private MutableLiveData<Integer> value = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsStarted() {
        return isStarted;
    }

    public MutableLiveData<Integer> getValue() {
        return value;
    }

    public void startCounter() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                try {
                    int i = 0;
                    while (i < 100) {
                        emitter.onNext(i++);
                        Thread.sleep(1000);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.e(getClass().getName(), "onComplete");
                        isStarted.postValue(false);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.e(getClass().getName(), "onError");
                        isStarted.postValue(false);
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        //show progress dialog
                        Log.e(getClass().getName(), "onSubcribe");
                        isStarted.postValue(true);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    //nhận giá trị và cập nhật giao diện
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        Log.e(getClass().getName(), "onData: " + integer);
                        value.postValue(integer);
                    }
                });


    }
}
