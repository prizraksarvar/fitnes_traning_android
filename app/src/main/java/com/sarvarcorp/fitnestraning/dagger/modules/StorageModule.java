package com.sarvarcorp.fitnestraning.dagger.modules;

import android.content.Context;

import com.sarvarcorp.fitnestraning.dagger.StaticData;
import com.sarvarcorp.fitnestraning.repositories.MobileappRepository;
import com.sarvarcorp.fitnestraning.repositories.UniversalItemRepository;
import com.sarvarcorp.fitnestraning.workers.AppDatabase;
import com.sarvarcorp.fitnestraning.workers.Webservice;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ContextModule.class)
public class StorageModule {

    @Provides
    public AppDatabase provideAppDatabase(StaticData staticData, Context context) {
        return staticData.getAppDatabase(context);
    }

    @Provides
    public Webservice provideWebservice() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://artifact-guide.sarvarcorp.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Webservice.class);
    }

    @Provides
    public UniversalItemRepository provideUniversalItemRepository(Webservice webservice, AppDatabase appDatabase, Executor executor) {
        return new UniversalItemRepository(webservice, appDatabase.universalItemDao(), executor);
    }

    @Provides
    public MobileappRepository provideMobileappRepository(Webservice webservice, AppDatabase appDatabase, Executor executor) {
        return new MobileappRepository(webservice, appDatabase.mobileappDao(), appDatabase.mobileappConfigDao(), executor);
    }
}