package com.sarvarcorp.fitnestraning.dagger;

import android.content.Context;

import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.MainActivity;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.MobileappConfig;
import com.sarvarcorp.fitnestraning.fragments.TimerFragment;
import com.sarvarcorp.fitnestraning.workers.AppDatabase;
import com.sarvarcorp.fitnestraning.workers.FragmentWorker;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    private HashMap<String,String> mobileappConfigs;
    private int mobileappId;
    private FragmentWorker fragmentWorker;
    private MainActivity mainActivity;
    private TimerFragment timerFragment;

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
                    .addMigrations(AppDatabase.MIGRATION_1_2)
                    .build();
        }
        return appDatabase;
    }

    public HashMap<String, String> getMobileappConfigs() {
        return mobileappConfigs;
    }

    public String getMobileappConfigsLocaleValue(String code) {
        return getMobileappConfigs().get(code+"|"+getLanguage());
    }

    public String getMobileappConfigsValue(String code) {
        return getMobileappConfigs().get(code+"|en");
    }

    public void setMobileappConfigs(List<MobileappConfig> mobileappConfigs) {
        this.mobileappConfigs = new HashMap<>();
        for (MobileappConfig config: mobileappConfigs) {
            this.mobileappConfigs.put(config.code+"|"+config.lang,config.value);
        }
    }

    public TimerFragment getTimerFragment() {
        return timerFragment;
    }

    public void setTimerFragment(TimerFragment timerFragment) {
        this.timerFragment = timerFragment;
    }

    public String getLanguage() {
        return Locale.getDefault().getLanguage();
    }
}
