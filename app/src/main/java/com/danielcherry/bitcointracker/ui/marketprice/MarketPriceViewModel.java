package com.danielcherry.bitcointracker.ui.marketprice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.danielcherry.bitcointracker.data.MarketPriceRepository;
import com.danielcherry.bitcointracker.data.models.MarketPrice;
import com.danielcherry.bitcointracker.data.models.TimeSpan;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MarketPriceViewModel extends ViewModel {

    private final MarketPriceRepository mRepository;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    /**
     * LiveData that notifies successful MarketPrice fetches
     */
    private final MutableLiveData<MarketPrice> mMarketPriceData = new MutableLiveData<>();

    /**
     * LiveData that notifies the view when an error occurs while fetching the data
     */
    private final MutableLiveData<Throwable> mErrorEventData = new MutableLiveData<>();

    @Inject
    public MarketPriceViewModel(MarketPriceRepository repository) {
        mRepository = repository;
    }

    public LiveData<MarketPrice> getMarketPriceData() {
        return mMarketPriceData;
    }

    public LiveData<Throwable> getErrorEventData() {
        return mErrorEventData;
    }

    /**
     * Initial fetching method. If current data exists - refresh, else fetch default data.
     */
    public void fetchMarketPriceData() {
        if (mMarketPriceData.getValue() != null) {
            // Refresh the current MarketPrice
            fetchMarketPriceData(mMarketPriceData.getValue().getTimeSpan());
        } else {
            // Get default MarketPrice
            fetchMarketPriceData(null);
        }
    }

    public void fetchMarketPriceData(int quantity, TimeSpan.Unit unit) {
        fetchMarketPriceData(new TimeSpan(quantity, unit).toString());
    }

    public void fetchMarketPriceData(String timeSpan) {
        mDisposables.add(mRepository.fetchMarketPrice(timeSpan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MarketPrice>() {
                    @Override
                    public void onSuccess(MarketPrice marketPrice) {
                        mMarketPriceData.postValue(marketPrice);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mErrorEventData.postValue(e);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        mDisposables.dispose();
    }

}
