package com.cooldevs.exercisesflexibility.workers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.cooldevs.exercisesflexibility.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.fragments.LoadingDialogFragment;

public class AdRewardedWorker implements RewardedVideoAdListener {
    private final RewardedVideoAd mRewardedVideoAd;
    protected int countSkip = 0;
    protected InterstitialAd mInterstitialAd;
    protected Context context;
    protected boolean needShow = false;
    protected String currentCode = "";
    protected boolean isNeedShow = false;
    protected LoadingDialogFragment loadingDialogFragment;

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    public AdRewardedWorker(Context context) {
        this.context = context;
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadingDialogFragment = new LoadingDialogFragment();
    }

    protected void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(context.getString(R.string.adsRewardedVideoID), new AdRequest.Builder().build());
    }

    protected SharedPreferences getPreferences() {
        return context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE);
    }

    public boolean isShown(String code) {
        return getPreferences().getBoolean("app-ads-rewarded-video" + code,false);
    }

    public void show() {
        if (!isNeedShow)
            loadRewardedVideoAd();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {
            isNeedShow = true;
            loadingDialogFragment.show(App.getComponent().provideStaticData().getFragmentManager(),loadingDialogFragment.getClass().getName());
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if (isNeedShow) {
            isNeedShow = false;
            loadingDialogFragment.dismiss();
            mRewardedVideoAd.show();
        }
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
        App.getComponent().provideExecutor().execute(()->{
            App.getComponent().provideAppDatabase().universalItemDao().setBonusAvailable(Integer.parseInt(currentCode));
            getPreferences().edit().putBoolean("app-ads-rewarded-video" + currentCode,true).apply();
        });
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        if (isNeedShow) {
            loadingDialogFragment.dismiss();
            isNeedShow = false;
            Toast.makeText(App.getComponent().porvideContext(),"Rewarded Ad load fail",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}