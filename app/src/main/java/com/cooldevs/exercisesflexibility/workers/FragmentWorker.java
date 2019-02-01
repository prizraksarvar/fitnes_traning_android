package com.cooldevs.exercisesflexibility.workers;


import android.os.Build;
import android.os.Handler;
import android.view.View;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.Base;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.fragments.AdBannerFragment;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@Singleton
public class FragmentWorker extends Base implements FragmentManager.OnBackStackChangedListener {
    private FragmentManager fragmentManager;
    private Class<?> currentFragment;

    private int toAdsShowCounter = 5;
    private Random random = new Random(System.currentTimeMillis());

    @Inject
    public FragmentWorker(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        fragmentManager.addOnBackStackChangedListener(this);
    }

    public void showFragment(Class<?> clazz, boolean addToBackStack) {
        try {
            BaseFragment fragment = getFragment(clazz);
            showFragment(clazz,addToBackStack,fragment);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            return;
        }
    }

    public void showFragment(Class<?> clazz, boolean addToBackStack, BaseFragment fragment) {
        showFragment(clazz,addToBackStack, fragment, fragment.getView());
    }

    private void setFragmentShowAnimations(FragmentTransaction fragmentTranaction, boolean addToBackStack, BaseFragment fragment, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && currentFragment!=null) {
            BaseFragment oldFragment = getCurrentFragment();
            fragment.prepareAnimation(fragmentTranaction, oldFragment, view);

            if (addToBackStack) {
                fragment.prepareEnterAnimation(fragmentTranaction, oldFragment, view);
                oldFragment.prepareExitAnimation(fragmentTranaction, fragment, view);
            } else {
                fragment.prepareReturnAnimation(fragmentTranaction, oldFragment, view);
                oldFragment.prepareExitAnimation(fragmentTranaction, oldFragment, view);
            }
        }
    }

    private void prepareAds() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                AdInterstitialWorker.getInstance().showAdsIfNeed();
            }
        },300);
    }

    public void showFragment(Class<?> clazz, boolean addToBackStack, BaseFragment fragment, View view) {
        if (fragmentManager.isStateSaved())
            return;
        FragmentTransaction fragmentTranaction = fragmentManager.beginTransaction();

        setFragmentShowAnimations(fragmentTranaction, addToBackStack, fragment, view);

        //if (currentFragment==null) {
        //    fragmentTranaction.add(R.id.mainContainer, fragment);
        //} else {
            fragmentTranaction.replace(R.id.mainContainer, fragment);
            if (addToBackStack) {
                fragmentTranaction.addToBackStack(clazz.getName());
            }
        //}
        currentFragment = clazz;
        fragmentTranaction.commit();
    }

    public void restoreFragmentsState() {
        List<Fragment> fs = fragmentManager.getFragments();
        Fragment fragment = fs.get(fs.size()-1);
        showFragment(fragment.getClass(),false, (BaseFragment) fragment, fragment.getView());
    }

    public BaseFragment getCurrentFragment() {
        int i = fragmentManager.getFragments().size();
        while (i>0) {
            if (fragmentManager.getFragments().get(i - 1) instanceof BaseFragment) {
                return (BaseFragment) fragmentManager.getFragments().get(i - 1);
            }
            i--;
        }
        return null;
    }

    public BaseFragment getFragment(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return (BaseFragment) clazz.getDeclaredConstructor().newInstance();
    }

    @Override
    public void onBackStackChanged() {
        prepareAds();
        if (fragmentManager.getFragments().size()>0) {
            Fragment fragment = getCurrentFragment();
            Class<?> aClass = fragment.getClass();
        } else {
            currentFragment = null;
        }
    }

    public void onActivityDestroy() {
        //currentFragment = null;
    }
}
