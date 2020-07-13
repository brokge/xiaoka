package com.yusuzi.xiaoka.ui.card;

import com.yusuzi.xiaoka.api.CardDataSource;
import com.yusuzi.xiaoka.common.EnumResultState;
import com.yusuzi.xiaoka.persistence.Card;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CardEditViewModel extends ViewModel {

    CardDataSource                   cardDataSource;
    MutableLiveData<Boolean>         loadingState;
    MutableLiveData<EnumResultState> resultMessage;
    MutableLiveData<Card> cardMutableLiveData;

    public CardEditViewModel (CardDataSource cardDataSource) {
        this.cardDataSource = cardDataSource;
    }

    public MutableLiveData<EnumResultState> getResultMessage () {
        if(resultMessage == null) {
            resultMessage = new MutableLiveData<>();
            resultMessage.setValue(EnumResultState.NORMAL);
        }
        return resultMessage;
    }

    public MutableLiveData<Card> getCardMutableLiveData () {
        if(cardMutableLiveData == null) {
            cardMutableLiveData = new MutableLiveData<>();
        }
        return cardMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoadingState () {
        if(loadingState == null) {
            loadingState = new MutableLiveData<>();
            loadingState.setValue(false);
        }
        return loadingState;
    }

    public Disposable getCard (String id) {
        loadingState.setValue(true);
        return cardDataSource.getCard(new String[] {id})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cards -> {
                    loadingState.setValue(false);
                    cardMutableLiveData.setValue(cards.get(0));
                }, throwable -> {
                    loadingState.setValue(false);
                });
    }

    public Disposable saveCard (Card card) {
        loadingState.setValue(true);
        return cardDataSource.insertOrUpdateCard(card)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    loadingState.setValue(false);
                    resultMessage.setValue(EnumResultState.SUCCESS);
                }, throwable -> {
                    resultMessage.setValue(EnumResultState.FAIL);
                });
    }


}