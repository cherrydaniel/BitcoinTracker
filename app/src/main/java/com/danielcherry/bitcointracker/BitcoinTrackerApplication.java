package com.danielcherry.bitcointracker;

import android.app.Application;

import com.danielcherry.bitcointracker.di.components.ApplicationComponent;
import com.danielcherry.bitcointracker.di.components.DaggerApplicationComponent;
import com.danielcherry.bitcointracker.di.modules.ApplicationModule;

import timber.log.Timber;

public class BitcoinTrackerApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
