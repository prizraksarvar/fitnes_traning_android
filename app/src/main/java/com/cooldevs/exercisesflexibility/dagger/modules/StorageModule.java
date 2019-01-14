package com.cooldevs.exercisesflexibility.dagger.modules;

import android.content.Context;

import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.dagger.StaticData;
import com.cooldevs.exercisesflexibility.repositories.MobileappRepository;
import com.cooldevs.exercisesflexibility.repositories.UniversalItemRepository;
import com.cooldevs.exercisesflexibility.workers.AppDatabase;
import com.cooldevs.exercisesflexibility.workers.Webservice;

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
    public Webservice provideWebservice(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.app_backend_api_url))
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