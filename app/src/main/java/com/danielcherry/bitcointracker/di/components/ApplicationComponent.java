package com.danielcherry.bitcointracker.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModelProvider;

import com.danielcherry.bitcointracker.data.MarketPriceRepository;
import com.danielcherry.bitcointracker.data.remote.MarketPriceRemoteSource;
import com.danielcherry.bitcointracker.data.remote.MarketPriceService;
import com.danielcherry.bitcointracker.di.modules.ApplicationModule;
import com.danielcherry.bitcointracker.di.modules.MarketPriceServiceModule;
import com.danielcherry.bitcointracker.di.modules.NetworkModule;
import com.danielcherry.bitcointracker.di.modules.ViewModelFactoryModule;
import com.danielcherry.bitcointracker.di.modules.ViewModelModule;
import com.danielcherry.bitcointracker.di.qualifiers.ApplicationContext;
import com.danielcherry.bitcointracker.ui.main.MainActivity;
import com.danielcherry.bitcointracker.ui.marketprice.MarketPriceFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                MarketPriceServiceModule.class,
                NetworkModule.class,
                ViewModelModule.class,
                ViewModelFactoryModule.class
        })
public interface ApplicationComponent {

    void inject(MarketPriceFragment marketPriceFragment);

    @ApplicationContext
    Context getApplicationContext();

    MarketPriceService getMarketPriceService();

    MarketPriceRepository getMarketPriceRepository();

    MarketPriceRemoteSource getMarketPriceRemoteSource();

    ViewModelProvider.Factory getViewModelFactory();

}
