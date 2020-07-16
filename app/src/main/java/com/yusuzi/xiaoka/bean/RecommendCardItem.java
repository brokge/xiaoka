package com.yusuzi.xiaoka.bean;

import com.yusuzi.xiaoka.common.EnumItemType;

import java.io.Serializable;

public class RecommendCardItem extends Item implements Serializable {

    public String id;
    public String name;
    public String amount;
    public String recommendReason;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getAmount () {
        return amount;
    }

    public void setAmount (String amount) {
        this.amount = amount;
    }

    public String getRecommendReason () {
        return recommendReason;
    }

    public void setRecommendReason (String recommendReason) {
        this.recommendReason = recommendReason;
    }

    @Override
    public EnumItemType getType () {
        return EnumItemType.ITEM_CHILD;
    }
}
