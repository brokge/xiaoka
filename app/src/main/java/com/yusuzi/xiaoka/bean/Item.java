package com.yusuzi.xiaoka.bean;

import com.yusuzi.xiaoka.common.EnumItemType;

public abstract class Item {
    public int position;

    public abstract EnumItemType getType();
}
