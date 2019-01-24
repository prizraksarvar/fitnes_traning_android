package com.cooldevs.exercisesflexibility.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.fragments.universalitem.BaseUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.Card3UniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.CardUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.ImageListUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.TitleImageListUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.TitleListUniversalItemFragment;
import com.cooldevs.exercisesflexibility.workers.AdRewardedWorker;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;


public class UniversalItemsFragment extends BaseFragment {
    protected UniversalItem currentItem;
    protected BaseUniversalItemFragment currentBackendFragment;

    public UniversalItemsFragment() {

    }

    @Override
    protected int getLayoutID() {
        return currentBackendFragment.getLayoutID();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("app/test","Fragment:onCreate");
        loadCurrentItemState(savedInstanceState);
        super.onCreate(savedInstanceState);
        currentBackendFragment.onCreate();
    }

    public UniversalItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(UniversalItem currentItem) {
        Log.d("app/test","Fragment:setCurrentItem");
        this.currentItem = currentItem;
        initCurrentBackendFragment();
    }

    protected void initCurrentBackendFragment() {
        String viewType = "";
        if (currentItem != null) {
            viewType = currentItem.viewType;
        }
        boolean isList = false;
        switch (viewType) {
            case "list_with_image": currentBackendFragment = new ImageListUniversalItemFragment(); isList = true; break;
            case "list_with_image_title": currentBackendFragment = new TitleImageListUniversalItemFragment(); isList = true; break;
            case "cart": currentBackendFragment = new CardUniversalItemFragment(); break;
            case "cart_3": currentBackendFragment = new Card3UniversalItemFragment(); break;
            default: currentBackendFragment = new TitleListUniversalItemFragment(); isList = true;
        }
        currentBackendFragment.setCurrentItem(currentItem);
        currentBackendFragment.setFragment(this);
        AdRewardedWorker.getInstance().setCurrentCode(String.format(Locale.ENGLISH,"%d",isList?currentItem.id:currentItem.parentId));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("app/test","Fragment:onCreateView");
        View view = super.onCreateView(inflater, container, savedInstanceState);
        currentBackendFragment.onCreateView(view);
        return view;
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        currentBackendFragment.prepareEnterAnimation(fragmentTranaction,oldFragment,view);
    }

    private void loadCurrentItemState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState==null)
            return;
        currentItem = new UniversalItem();
        currentItem.id = savedInstanceState.getInt("UniversalItemsFragment::currentItem.id",0);
        Log.d("app/test","Fragment:loadCurrentItemState = " + savedInstanceState.toString());
        Log.d("app/test","Fragment:loadCurrentItemState = " + currentItem.id);
        currentItem.name = savedInstanceState.getString("UniversalItemsFragment::currentItem.name","");
        currentItem.parentId = savedInstanceState.getInt("UniversalItemsFragment::currentItem.parentId",0);
        currentItem.timerTime = savedInstanceState.getInt("UniversalItemsFragment::currentItem.timerTime",0);
        currentItem.backgroundColor = savedInstanceState.getString("UniversalItemsFragment::currentItem.backgroundColor","");
        currentItem.description = savedInstanceState.getString("UniversalItemsFragment::currentItem.description","");
        currentItem.image = savedInstanceState.getString("UniversalItemsFragment::currentItem.image","");
        currentItem.isAvailableBonus = savedInstanceState.getBoolean("UniversalItemsFragment::currentItem.isAvailableBonus",false);
        currentItem.isBonus = savedInstanceState.getBoolean("UniversalItemsFragment::currentItem.isBonus",false);
        currentItem.listBackgroundColor = savedInstanceState.getString("UniversalItemsFragment::currentItem.listBackgroundColor","");
        currentItem.listViewType = savedInstanceState.getString("UniversalItemsFragment::currentItem.listViewType","");
        currentItem.smallImage = savedInstanceState.getString("UniversalItemsFragment::currentItem.smallImage","");
        currentItem.updatedDate = savedInstanceState.getLong("UniversalItemsFragment::currentItem.updatedDate",0);
        currentItem.viewType = savedInstanceState.getString("UniversalItemsFragment::currentItem.viewType","");
        initCurrentBackendFragment();
    }

    private void saveCurrentItemState(@NonNull Bundle outState) {
        outState.putInt("UniversalItemsFragment::currentItem.id",currentItem.id);
        outState.putString("UniversalItemsFragment::currentItem.name",currentItem.name);
        outState.putInt("UniversalItemsFragment::currentItem.parentId",currentItem.parentId);
        outState.putInt("UniversalItemsFragment::currentItem.timerTime",currentItem.timerTime);
        outState.putString("UniversalItemsFragment::currentItem.backgroundColor",currentItem.backgroundColor);
        outState.putString("UniversalItemsFragment::currentItem.description",currentItem.description);
        outState.putString("UniversalItemsFragment::currentItem.image",currentItem.image);
        outState.putBoolean("UniversalItemsFragment::currentItem.isAvailableBonus",currentItem.isAvailableBonus);
        outState.putBoolean("UniversalItemsFragment::currentItem.isBonus",currentItem.isBonus);
        outState.putString("UniversalItemsFragment::currentItem.listBackgroundColor",currentItem.listBackgroundColor);
        outState.putString("UniversalItemsFragment::currentItem.listViewType",currentItem.listViewType);
        outState.putString("UniversalItemsFragment::currentItem.smallImage",currentItem.smallImage);
        outState.putLong("UniversalItemsFragment::currentItem.updatedDate",currentItem.updatedDate);
        outState.putString("UniversalItemsFragment::currentItem.viewType",currentItem.viewType);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("app/test","Fragment:onSaveInstanceState");
        saveCurrentItemState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("app/test","Fragment:onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }
}
