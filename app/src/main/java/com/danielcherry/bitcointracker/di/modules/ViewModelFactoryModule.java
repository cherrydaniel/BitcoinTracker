package com.danielcherry.bitcointracker.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.danielcherry.bitcointracker.di.viewmodels.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
