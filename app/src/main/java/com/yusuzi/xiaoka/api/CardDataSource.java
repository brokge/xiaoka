package com.yusuzi.xiaoka.api;


import com.yusuzi.xiaoka.persistence.Card;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


public interface CardDataSource {

    Flowable<List<Card>> getCard (String[] ids);

    Flowable<List<Card>> getCardList ();

    Completable insertOrUpdateCard (Card card);

    Completable delete (Card card);

    Completable update (Card card);
}
