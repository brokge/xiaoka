package com.yusuzi.xiaoka.persistence;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface CardDao {

    @Query("SELECT * FROM Card")
    Flowable<List<Card>> getAll ();

    @Query("SELECT * FROM card WHERE cid IN (:ids)")
    Flowable<List<Card>> loadAllByIds (String[] ids);


    @Query("SELECT * FROM Card WHERE card_name LIKE :name  LIMIT 1")
    Flowable<Card> findByName (String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll (Card... cards);

    @Delete
    Completable delete (Card card);

    @Update
    Completable update (Card card);
}
