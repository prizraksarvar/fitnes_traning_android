package com.cooldevs.exercisesflexibility;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.cooldevs.exercisesflexibility.dagger.AppComponent;
import com.cooldevs.exercisesflexibility.dagger.DaggerAppComponent;
import com.cooldevs.exercisesflexibility.dagger.StaticData;
import com.cooldevs.exercisesflexibility.dagger.modules.BaseModule;
import com.cooldevs.exercisesflexibility.dagger.modules.ContextModule;
import com.cooldevs.exercisesflexibility.workers.AdInterstitialWorker;
import com.cooldevs.exercisesflexibility.workers.AdRewardWorker;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class App extends Application {
    private static AppComponent component;
    private static AdInterstitialWorker adInterstitialWorker = null;
    private static AdRewardWorker adRewardWorker = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.adsApplicationID));
        adInterstitialWorker = new AdInterstitialWorker(this);
        adRewardWorker = new AdRewardWorker(this);
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .baseModule(new BaseModule(new StaticData()))
                .build();
        initYandexAppMetrica();
    }

    protected void initYandexAppMetrica() {
        String API_key = getString(R.string.yandex_metrica_app_id);
        if (API_key.equals(""))
            return;
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(API_key).build();
        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(this);
    }

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onTerminate() {
        adInterstitialWorker = null;
        super.onTerminate();
    }

    public static AdInterstitialWorker getAdInterstitialWorker() {
        return adInterstitialWorker;
    }

    public static AdRewardWorker getAdRewardWorker() {
        return adRewardWorker;
    }
}