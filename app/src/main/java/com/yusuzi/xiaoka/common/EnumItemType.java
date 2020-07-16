package com.yusuzi.xiaoka.common;

public enum EnumItemType {
    ITEM_GROUP(1), ITEM_CHILD(2);

    EnumItemType (int type) {
        this.value = type;
    }

    private int value;

    public int getValue () {
        return value;
    }

    public void setValue (int type) {
        this.value = type;
    }
}
