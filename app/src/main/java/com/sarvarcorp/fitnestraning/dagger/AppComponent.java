package com.sarvarcorp.fitnestraning.dagger;

import android.content.Context;

import com.sarvarcorp.fitnestraning.dagger.modules.BaseModule;
import com.sarvarcorp.fitnestraning.dagger.modules.ContextModule;
import com.sarvarcorp.fitnestraning.dagger.modules.StorageModule;
import com.sarvarcorp.fitnestraning.repositories.MobileappRepository;
import com.sarvarcorp.fitnestraning.repositories.UniversalItemRepository;
import com.sarvarcorp.fitnestraning.workers.AppDatabase;
import com.sarvarcorp.fitnestraning.workers.FragmentWorker;
import com.sarvarcorp.fitnestraning.workers.SettingWorker;

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