package com.yusuzi.xiaoka.ui;

import com.yusuzi.xiaoka.api.CardDataSource;
import com.yusuzi.xiaoka.ui.card.CardDetailViewModel;
import com.yusuzi.xiaoka.ui.card.CardEditViewModel;
import com.yusuzi.xiaoka.ui.dashboard.DashboardViewModel;
import com.yusuzi.xiaoka.ui.home.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final CardDataSource mDataSource;

    public ViewModelFactory (CardDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create (@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CardEditViewModel.class)) {
            return (T) new CardEditViewModel(mDataSource);
        }
        if(modelClass.isAssignableFrom(DashboardViewModel.class)){
            return (T)new DashboardViewModel(mDataSource);
        }
        if(modelClass.isAssignableFrom(CardDetailViewModel.class)){
            return (T)new CardDetailViewModel(mDataSource);
        }
        if(modelClass.isAssignableFrom(HomeViewModel.class)){
            return (T)new HomeViewModel(mDataSource);
        }
        //add other view model
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}