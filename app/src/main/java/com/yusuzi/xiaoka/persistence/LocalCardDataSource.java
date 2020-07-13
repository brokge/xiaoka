package com.yusuzi.xiaoka.persistence;

import android.text.TextUtils;

import com.yusuzi.xiaoka.api.CardDataSource;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalCardDataSource implements CardDataSource {
    public CardDao cardDao;

    public LocalCardDataSource (CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public Flowable<List<Card>> getCard (String[] ids) {
        return cardDao.loadAllByIds(ids);
    }

    @Override
    public Flowable<List<Card>> getCardList () {
        return cardDao.getAll();
    }

    @Override
    public Completable insertOrUpdateCard (Card card) {
        if(TextUtils.isEmpty(card.cid)) {
            card.cid = UUID.randomUUID().toString();
        }
        if(card.createDateTime == 0) {
            card.createDateTime = System.currentTimeMillis();
        }
        if(card.updateDateTime == 0) {
            card.updateDateTime = System.currentTimeMillis();
        }
        card.ver = card.ver + 1;
        card.isValid = 1;
        return cardDao.insertAll(card);
    }

    @Override
    public Completable delete (Card card) {
       return cardDao.delete(card);
    }

    @Override
    public Completable update (Card card) {
        if(card.updateDateTime == 0) {
            card.updateDateTime = System.currentTimeMillis();
        }
        card.ver = card.ver + 1;
        card.isValid = 1;
        return cardDao.update(card);
    }
}
