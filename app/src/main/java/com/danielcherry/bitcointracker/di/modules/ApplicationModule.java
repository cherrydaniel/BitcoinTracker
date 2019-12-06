package com.danielcherry.bitcointracker.di.modules;

import android.content.Context;

import com.danielcherry.bitcointracker.BitcoinTrackerApplication;
import com.danielcherry.bitcointracker.di.qualifiers.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private BitcoinTrackerApplication application;

    public ApplicationModule(BitcoinTrackerApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return application;
    }

}
