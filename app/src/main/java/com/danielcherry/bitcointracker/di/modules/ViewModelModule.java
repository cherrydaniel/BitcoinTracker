package com.danielcherry.bitcointracker.di.modules;

import androidx.lifecycle.ViewModel;

import com.danielcherry.bitcointracker.di.viewmodels.ViewModelKey;
import com.danielcherry.bitcointracker.ui.marketprice.MarketPriceViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MarketPriceViewModel.class)
    public abstract ViewModel bindMainViewModel(MarketPriceViewModel marketPriceViewModel);

}
