package com.sarvarcorp.fitnestraning.dagger;

import android.content.Context;

import com.sarvarcorp.fitnestraning.MainActivity;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.MobileappConfig;
import com.sarvarcorp.fitnestraning.workers.AppDatabase;
import com.sarvarcorp.fitnestraning.workers.FragmentWorker;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

@Singleton
public class StaticData {
    @Inject
    public StaticData() {
    }

    private FragmentManager fragmentManager;
    private AppDatabase appDatabase;
    private String appApiKey;
    private Mobileapp mobileapp;
    private List<MobileappConfig> mobileappConfigs;
    private int mobileappId;
    private FragmentWorker fragmentWorker;
    private MainActivity mainActivity;

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getAppApiKey() {
        return appApiKey;
    }

    public void setAppApiKey(String appApiKey) {
        this.appApiKey = appApiKey;
    }

    public Mobileapp getMobileapp() {
        return mobileapp;
    }

    public void setMobileapp(Mobileapp mobileapp) {
        this.mobileapp = mobileapp;
    }

    public int getMobileappId() {
        return mobileappId;
    }

    public void setMobileappId(int mobileappId) {
        this.mobileappId = mobileappId;
    }

    public FragmentWorker getFragmentWorker() {
        if (fragmentWorker==null) {
            fragmentWorker = new FragmentWorker(getFragmentManager());
        }
        return fragmentWorker;
    }

    public AppDatabase getAppDatabase(Context context) {
        if (appDatabase==null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database")
                    .addMigrations()
                    .build();
        }
        return appDatabase;
    }

    public List<MobileappConfig> getMobileappConfigs() {
        return mobileappConfigs;
    }

    public void setMobileappConfigs(List<MobileappConfig> mobileappConfigs) {
        this.mobileappConfigs = mobileappConfigs;
    }
}
