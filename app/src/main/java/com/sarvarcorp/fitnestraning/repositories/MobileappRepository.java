package com.sarvarcorp.fitnestraning.repositories;

import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.daos.MobileappConfigDao;
import com.sarvarcorp.fitnestraning.daos.MobileappDao;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.MobileappConfig;
import com.sarvarcorp.fitnestraning.workers.Webservice;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Response;

@Singleton
public class MobileappRepository {
    private static final long FRESH_TIMEOUT = 300000; //5min
    private final Webservice webservice;
    private final MobileappDao mobileappDao;
    private final MobileappConfigDao mobileappConfigDao;
    private final Executor executor;

    @Inject
    public MobileappRepository(Webservice webservice, MobileappDao mobileappDao, MobileappConfigDao mobileappConfigDao, Executor executor) {
        this.webservice = webservice;
        this.mobileappDao = mobileappDao;
        this.mobileappConfigDao = mobileappConfigDao;
        this.executor = executor;
    }

    public LiveData<Mobileapp> get() {
        refreshUniversalItem();
        return mobileappDao.get();
    }

    private void refreshUniversalItem() {
        executor.execute(() -> {
            Response<MobileappResponseAdapter> response = null;
            try {
                long time = System.currentTimeMillis();
                int updatesOnly = mobileappDao.lastUpdatedDate();
                if (mobileappDao.isUnfresh(time-FRESH_TIMEOUT)) {
                    updatesOnly = 0;
                }
                response = webservice.getMobileapp(
                        App.getComponent().provideStaticData().getAppApiKey(),
                        App.getComponent().provideStaticData().getMobileappId(), updatesOnly, App.getComponent().provideStaticData().getLanguage()).execute();

                if (response.isSuccessful() && response.body() != null) {
                    int[] ids = new int[response.body().configs.size()];
                    int i = 0;
                    for (MobileappConfig config :response.body().configs) {
                        mobileappConfigDao.save(config);
                        ids[i] = config.id;
                        i++;
                    }
                    if (updatesOnly==0)
                        mobileappConfigDao.deleteNotIds(ids);

                    mobileappDao.save(response.body().data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

