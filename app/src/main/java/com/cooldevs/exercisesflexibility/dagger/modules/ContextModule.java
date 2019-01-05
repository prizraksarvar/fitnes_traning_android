package com.cooldevs.exercisesflexibility.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    public Context porvideContext(){ return context.getApplicationContext(); }
}