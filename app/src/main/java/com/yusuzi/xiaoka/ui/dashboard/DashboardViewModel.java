package com.yusuzi.xiaoka.ui.dashboard;

import com.yusuzi.xiaoka.api.CardDataSource;
import com.yusuzi.xiaoka.bean.CardContent;
import com.yusuzi.xiaoka.bean.CardListContent;
import com.yusuzi.xiaoka.persistence.Card;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<CardListContent> mutableLiveData;
    private CardDataSource                   cardDataSource;

    public DashboardViewModel (CardDataSource cardDataSource) {
        this.cardDataSource = cardDataSource;
    }

    public LiveData<CardListContent> getCardListLiveData () {
        if(mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public Disposable getCardList () {
        return cardDataSource.getCardList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cards -> {
                    CardListContent cardListContent = new CardListContent();
                    cardListContent.setCardList(cards);
                    mutableLiveData.setValue(cardListContent);
                }, throwable -> {

                });
    }

    public void initCardList () {
        new Thread(new Runnable() {
            @Override
            public void run () {
                mutableLiveData.postValue(new CardListContent());
            }
        }).start();
    }
}