package com.sarvarcorp.fitnestraning.workers;

import android.content.Context;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sarvarcorp.fitnestraning.R;
import java.util.*;

public class AdInterstitialWorker {
    protected int countSkip = 0;
    protected InterstitialAd mInterstitialAd;
    protected Context context;
    protected boolean needShow = false;
    public AdInterstitialWorker(Context context) {
        this.context = context;
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.adsInterstitialID));
        loadAds();

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadAds();
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