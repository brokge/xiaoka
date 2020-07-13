package com.yusuzi.xiaoka.injection;

import android.content.Context;

import com.yusuzi.xiaoka.api.CardDataSource;
import com.yusuzi.xiaoka.persistence.CardDatabase;
import com.yusuzi.xiaoka.persistence.LocalCardDataSource;
import com.yusuzi.xiaoka.ui.ViewModelFactory;

public class Injections {

    public static CardDataSource provideUserDataSource (Context context) {
        CardDatabase database = CardDatabase.getInstance(context);
        return new LocalCardDataSource(database.cardDao());
    }

    public static ViewModelFactory provideViewModelFactory (Context context) {
        CardDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
