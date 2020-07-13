package com.yusuzi.xiaoka.common;

public enum EnumHandleType {
    EDIT(1), DETAIL(2),ADD(3);

    private int value;

    EnumHandleType (int value) {
        this.value = value;
    }

    public int getValue () {
        return value;
    }

    public void setValue (int value) {
        this.value = value;
    }

    public static EnumHandleType getType (int value) {
        EnumHandleType[] values = EnumHandleType.values();
        for (EnumHandleType enumHandleType : values) {
            if(value == enumHandleType.getValue()) {
                return enumHandleType;
            }
        }
        return EnumHandleType.DETAIL;
    }
}
