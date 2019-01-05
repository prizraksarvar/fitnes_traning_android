package com.sarvarcorp.fitnestraning.workers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.sarvarcorp.fitnestraning.R;

import java.util.Random;

public class AdRewardWorker implements RewardedVideoAdListener {
    private final RewardedVideoAd mRewardedVideoAd;
    protected int countSkip = 0;
    protected InterstitialAd mInterstitialAd;
    protected Context context;
    protected boolean needShow = false;
    protected String currentCode = "";
    public AdRewardWorker(Context context) {
        this.context = context;
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();
    }

    protected void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(context.getString(R.string.adsRewardedVideoID),
                new AdRequest.Builder().build());
    }

    protected SharedPreferences getPreferences() {
        return context.getSharedPreferences("app-ads-rewarded-video", 0);
    }

    public boolean isShown(String code) {
        return getPreferences().getBoolean(code,false);
    }

    public void show(String code) {
        currentCode = code;
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {

        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        getPreferences().edit().putBoolean(currentCode,false).apply();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}