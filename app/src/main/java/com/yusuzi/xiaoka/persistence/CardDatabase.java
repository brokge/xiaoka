package com.yusuzi.xiaoka.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Card.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {

    public abstract CardDao cardDao ();

    /**
     * The only instance
     */

    private static CardDatabase sInstance;

    /**
     * Gets the singleton instance of SampleDatabase.
     */
    public static synchronized CardDatabase getInstance (Context context) {
        if(sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), CardDatabase.class, "card")
                    .build();
            //sInstance.populateInitialData();
        }
        return sInstance;
    }
}

