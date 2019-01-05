package com.cooldevs.exercisesflexibility.dagger.modules;

import android.content.Context;


import com.cooldevs.exercisesflexibility.base.BaseExecuter;
import com.cooldevs.exercisesflexibility.dagger.StaticData;
import com.cooldevs.exercisesflexibility.workers.FragmentWorker;
import com.cooldevs.exercisesflexibility.workers.SettingWorker;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {
    private StaticData staticData;

    public BaseModule(StaticData staticData) {
        this.staticData = staticData;
    }

    @Provides
    public Executor provideExecutor() {
        return new BaseExecuter();
    }

    @Provides
    public StaticData provideStaticData() {
        return staticData;
    }

    @Provides
    public SettingWorker provideSettingWorker(Context context) {
        return new SettingWorker(context);
    }

    @Provides
    public FragmentWorker provideFragmentWorker(StaticData staticData) {
        return staticData.getFragmentWorker();
    }
}
