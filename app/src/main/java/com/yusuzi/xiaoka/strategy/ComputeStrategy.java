package com.yusuzi.xiaoka.strategy;

import com.yusuzi.xiaoka.bean.Item;

import java.util.List;

public interface ComputeStrategy {
    List<? extends Item> compute (int amount);
}
