package com.yusuzi.xiaoka.bean;

import com.yusuzi.xiaoka.persistence.Card;

import java.util.List;

public class CardListContent {
    List<Card> cardList;

    public CardListContent () {

    }

    public List<Card> getCardList () {
        return cardList;
    }

    public void setCardList (List<Card> cardList) {
        this.cardList = cardList;
    }
}
