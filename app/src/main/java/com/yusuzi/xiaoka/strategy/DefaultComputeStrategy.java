package com.yusuzi.xiaoka.strategy;

import android.content.Context;

import com.yusuzi.xiaoka.api.CardDataSource;
import com.yusuzi.xiaoka.bean.Item;
import com.yusuzi.xiaoka.bean.RecommendCardItem;
import com.yusuzi.xiaoka.common.DateInfoUtil;
import com.yusuzi.xiaoka.injection.Injections;
import com.yusuzi.xiaoka.persistence.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;

public class DefaultComputeStrategy implements ComputeStrategy {
    CardDataSource cardDataSource;

    public DefaultComputeStrategy (Context context) {
        cardDataSource = Injections.provideUserDataSource(context);
    }

    @Override
    public List<? extends Item> compute (int amount) {
        Flowable<List<Card>> cardList = cardDataSource.getCardList();
        List<Card>           list     = getSortByDistance(cardList.blockingFirst());
        return convertItem(list);
    }

    /**
     * 根据 账单日期来排序，距离账单日最长的排到前面
     *
     * @param cards 待排序的卡集合
     *
     * @return
     */
    public List<Card> getSortByDistance (List<Card> cards) {

        int days = DateInfoUtil.getCurrentMonthDays();
        int day  = DateInfoUtil.getCurrentDay();
        Collections.sort(cards, (o1, o2) -> {
            //
            return getCardDays(o1, day, days) >= getCardDays(o2, day, days) ? 1 : 0;
        });
        return cards;
    }

    public int getCardDays (Card card, int currentDay, int currentMonthDays) {
        int days = 0;
        //已经出账
        if(currentDay > card.cardBillDate) {
            days = currentMonthDays - currentDay + card.cardBillDate;
        } else {
            // 还未来出账
            days = card.cardBillDate - currentDay;
        }
        return days;
    }

    private List<RecommendCardItem> convertItem (List<Card> cards) {
        List<RecommendCardItem> itemList = new ArrayList<>();

        int days = DateInfoUtil.getCurrentMonthDays();
        int day  = DateInfoUtil.getCurrentDay();

        for (Card card : cards) {
            RecommendCardItem recommendCardItem = new RecommendCardItem();
            recommendCardItem.setName(card.cardName);
            recommendCardItem.setId(card.cid);
            recommendCardItem.setAmount(String.valueOf(card.cardAmount));
            recommendCardItem.setRecommendReason("距离账单日最长：" + getCardDays(card, day, days) + "天");
            itemList.add(recommendCardItem);
        }
        return itemList;
    }
}
