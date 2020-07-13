package com.yusuzi.xiaoka.persistence;

import androidx.room.ColumnInfo;

public class BaseEntity {

    @ColumnInfo(name = "create_date_time")
    public long createDateTime;

    @ColumnInfo(name = "update_date_time")
    public long updateDateTime;

    @ColumnInfo(name = "ver")
    public int ver;

    @ColumnInfo(name = "is_valid")
    public int isValid;
}
