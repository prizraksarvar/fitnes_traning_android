package com.cooldevs.exercisesflexibility.dagger;

import android.content.Context;

import com.cooldevs.exercisesflexibility.dagger.modules.BaseModule;
import com.cooldevs.exercisesflexibility.dagger.modules.ContextModule;
import com.cooldevs.exercisesflexibility.dagger.modules.StorageModule;
import com.cooldevs.exercisesflexibility.repositories.MobileappRepository;
import com.cooldevs.exercisesflexibility.repositories.UniversalItemRepository;
import com.cooldevs.exercisesflexibility.workers.AppDatabase;
import com.cooldevs.exercisesflexibility.workers.FragmentWorker;
import com.cooldevs.exercisesflexibility.workers.SettingWorker;

import java.util.concurrent.Executor;

import dagger.Component;

@Component(modules = {
        BaseModule.class,
        StorageModule.class,
        ContextModule.class
})
public interface AppComponent {
    //Context
    Context porvideContext();

    //Base
    SettingWorker provideSettingWorker();
    FragmentWorker provideFragmentWorker();
    StaticData provideStaticData();

    //Storage
    AppDatabase provideAppDatabase();
    Executor provideExecutor();
    UniversalItemRepository provideUniversalItemRepository();
    MobileappRepository provideMobileappRepository();
}