package com.sarvarcorp.fitnestraning;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.sarvarcorp.fitnestraning.base.BaseAppCompatActivity;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.MobileappConfig;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.fragments.LoadingFragment;
import com.sarvarcorp.fitnestraning.fragments.UniversalItemsFragment;
import com.sarvarcorp.fitnestraning.models.MobileappViewModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseAppCompatActivity implements Observer<Mobileapp> {
    private String activityState = "";
    private Toolbar toolbar;
    private AdView mAdView;
    private MobileappViewModel viewModel;
    private LiveData<Mobileapp> mobileappLiveData;

    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getComponent().provideStaticData().setFragmentManager(getSupportFragmentManager());
        App.getComponent().provideStaticData().setMainActivity(this);
        App.getComponent().provideStaticData().setAppApiKey(getResources().getString(R.string.app_backend_api_key));
        App.getComponent().provideStaticData().setMobileappId(getResources().getInteger(R.integer.app_backend_app_id));

        loading = true;
        toolbar = this.findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        showLoadingFragment();
        toolbar.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(MobileappViewModel.class);
        viewModel.init();
        mobileappLiveData = viewModel.get();

        mobileappLiveData.observe(this,this);
    }

    protected void showLoadingFragment() {
        App.getComponent().provideFragmentWorker().showFragment(LoadingFragment.class, false);
    }

    protected void showStartFragment(Mobileapp mobileapp) {
        App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class, false);
        try {
            //toolbar.setVisibility(View.VISIBLE);
            UniversalItem universalItem = new UniversalItem();
            universalItem.id = 0;
            universalItem.name = mobileapp.name;
            universalItem.viewType = mobileapp.viewType;
            universalItem.backgroundColor = mobileapp.backgroundColor;
            universalItem.description = mobileapp.description;
            universalItem.image = mobileapp.image;
            UniversalItemsFragment fragment = null;
            fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
            fragment.setCurrentItem(universalItem);
            App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class,true, fragment, null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        App.getComponent().provideFragmentWorker().onActivityDestroy();

        super.onDestroy();
        System.exit(0);
    }

    @Override
    public void onChanged(Mobileapp mobileapp) {
        if (mobileapp == null || mobileapp.id == 0) {
            return;
        }
        App.getComponent().provideStaticData().setMobileapp(mobileapp);
        if (loading) {
            loading=false;
            showStartFragment(mobileapp);
        } else {
            onMobileappUpdate();
        }
    }

    protected void onMobileappUpdate() {
        //TODO: need implement live theme update, I think need broadcast event
    }
}
