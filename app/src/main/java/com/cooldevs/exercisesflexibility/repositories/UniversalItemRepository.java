package com.cooldevs.exercisesflexibility.repositories;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.daos.UniversalItemDao;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.workers.Webservice;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Response;

@Singleton
public class UniversalItemRepository {
    private static final long FRESH_TIMEOUT = 300000; //5min
    private final Webservice webservice;
    private final UniversalItemDao universalItemDao;
    private final Executor executor;

    @Inject
    public UniversalItemRepository(Webservice webservice, UniversalItemDao universalItemDao, Executor executor) {
        this.webservice = webservice;
        this.universalItemDao = universalItemDao;
        this.executor = executor;
    }

    public LiveData<UniversalItem> get(int id) {
        refreshUniversalItem(id);
        return universalItemDao.get(id);
    }

    public LiveData<List<UniversalItem>> getList(int parentId) {
        refreshUniversalItems(parentId);
        return universalItemDao.getList(parentId);
    }

    private void refreshUniversalItems(final int parentId) {
        executor.execute(() -> {
            Response<ResponseAdapter<List<UniversalItem>>> response = null;
            try {
                long time = System.currentTimeMillis();
                int updatesOnly = universalItemDao.lastListUpdatedDate(parentId);
                if (universalItemDao.unfreshCount(parentId,time-FRESH_TIMEOUT)>0) {
                    updatesOnly = 0;
                }
                response = webservice.getUniversalItems(
                        App.getComponent().provideStaticData().getAppApiKey(),
                        App.getComponent().provideStaticData().getMobileapp().id,
                        parentId,
                        updatesOnly,
                        App.getComponent().provideStaticData().getLanguage()).execute();

                boolean isAvailableBonus = App.getAdRewardWorker().isShown(String.format(Locale.ENGLISH,"%d",parentId));
                if (response.isSuccessful() && response.body() != null) {
                    int[] ids = new int[response.body().data.size()];
                    int i = 0;
                    for (UniversalItem universalItem : response.body().data) {
                        universalItem.updatedDate = time;
                        universalItem.isAvailableBonus = isAvailableBonus;
                        universalItemDao.save(universalItem);
                        ids[i] = universalItem.id;
                        i++;
                    }
                    if (updatesOnly==0)
                        universalItemDao.deleteNotIds(parentId, ids);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void refreshUniversalItem(final int id) {
        executor.execute(() -> {
            Response<ResponseAdapter<UniversalItem>> response = null;
            try {
                long time = System.currentTimeMillis();
                int updatesOnly = universalItemDao.lastItemUpdatedDate(id);
                if (universalItemDao.isUnfresh(id,time-FRESH_TIMEOUT)) {
                    updatesOnly = 0;
                }
                response = webservice.getUniversalItem(
                        App.getComponent().provideStaticData().getAppApiKey(),
                        App.getComponent().provideStaticData().getMobileapp().id,
                        id,
                        updatesOnly,
                        App.getComponent().provideStaticData().getLanguage()).execute();

                if (response.isSuccessful() && response.body() != null) {
                    UniversalItem ui = response.body().data;
                    boolean isAvailableBonus = App.getAdRewardWorker().isShown(String.format(Locale.ENGLISH,"%d",ui.parentId));
                    ui.isAvailableBonus = isAvailableBonus;
                    universalItemDao.save(ui);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

