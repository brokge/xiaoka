package com.yusuzi.xiaoka.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card extends BaseEntity {
    @PrimaryKey
    @NonNull
    public String cid="";

    @ColumnInfo(name = "card_name")
    public String cardName;

    @ColumnInfo(name = "card_num")
    public String cardNum;

    @ColumnInfo(name = "card_amount")
    public int cardAmount;

    @ColumnInfo(name = "card_bill_date")
    public int cardBillDate;

    @ColumnInfo(name = "card_repayment_date")
    public int cardRepaymentDate;

}
