package com.yusuzi.xiaoka.ui.home;

import android.content.Context;

import com.yusuzi.xiaoka.api.CardDataSource;
import com.yusuzi.xiaoka.bean.Item;
import com.yusuzi.xiaoka.bean.RecommendCardItem;
import com.yusuzi.xiaoka.common.EnumResultState;
import com.yusuzi.xiaoka.strategy.ComputeStrategy;
import com.yusuzi.xiaoka.strategy.DefaultComputeStrategy;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private CardDataSource                   cardDataSource;
    private MutableLiveData<List<? extends Item>>      listMutableLiveData;
    private MutableLiveData<Boolean>         loadingState;
    private MutableLiveData<EnumResultState> resultMessage;

    public HomeViewModel (CardDataSource cardDataSource) {
        this.cardDataSource = cardDataSource;
    }

    public MutableLiveData<List<? extends Item>> getListMutableLiveData () {
        if(listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoadingState () {
        if(loadingState == null) {
            loadingState = new MutableLiveData<>();
        }
        return loadingState;
    }


    public MutableLiveData<EnumResultState> getResultMessage () {
        if(resultMessage == null) {
            resultMessage = new MutableLiveData<>();
        }
        return resultMessage;
    }

    public Disposable compute (Context context, int amount) {
        loadingState.setValue(true);
        ComputeStrategy computeStrategy = new DefaultComputeStrategy(context);
        return Flowable.fromCallable(() -> computeStrategy.compute(amount))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    loadingState.setValue(false);
                    resultMessage.setValue(EnumResultState.SUCCESS);
                    listMutableLiveData.setValue(items);
                }, throwable -> {
                    loadingState.setValue(false);
                    resultMessage.setValue(EnumResultState.FAIL);
                });
    }
}