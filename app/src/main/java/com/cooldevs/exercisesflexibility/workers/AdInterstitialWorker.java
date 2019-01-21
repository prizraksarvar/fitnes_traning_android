package com.cooldevs.exercisesflexibility.workers;

import android.content.Context;

import com.cooldevs.exercisesflexibility.App;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.cooldevs.exercisesflexibility.R;
import java.util.*;

public class AdInterstitialWorker {
    protected static AdInterstitialWorker thisInstance;
    protected int countSkip = 0;
    protected InterstitialAd mInterstitialAd;
    protected Context context;
    protected boolean needShow = false;

    public AdInterstitialWorker getInstance() {
        if (thisInstance == null) {
            thisInstance = new AdInterstitialWorker(App.getComponent().provideStaticData().getMainActivity());
        }
        return thisInstance;
    }

    public AdInterstitialWorker(Context context) {
        this.context = context;
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.adsInterstitialID));

        setRandomSkip();

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (needShow) {
                    showAds();
                }
            }
        });
    }

    protected void setRandomSkip() {
        Random i = new Random();
        i.setSeed(System.currentTimeMillis());
        int min = context.getResources().getInteger(R.integer.adsInterstitealIntervalMin);
        int max = context.getResources().getInteger(R.integer.adsInterstitealIntervalMax);
        countSkip = i.nextInt(max + 1 - min) + min;
    }

    protected void loadAds() {
        setRandomSkip();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public Boolean showAds() {
        if (!needShow)
            loadAds();
        if (mInterstitialAd.isLoaded()) {
            needShow = false;
            setRandomSkip();
            mInterstitialAd.show();
            return true;
        } else {
            needShow = true;
        }
        return false;
    }

    public void showAdsIfNeed() {
        countSkip--;
        if (countSkip<=0) {
            countSkip = 0;
            showAds();
        }
    }
}