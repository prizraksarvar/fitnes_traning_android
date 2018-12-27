package com.sarvarcorp.fitnestraning.workers;


import android.os.Build;
import android.os.Handler;
import android.view.View;

import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.Base;
import com.sarvarcorp.fitnestraning.base.BaseFragment;

import java.lang.reflect.InvocationTargetException;
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
        if (toAdsShowCounter==0) {

            int r = random.nextInt(3+1)+2;
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean showed = App.getAdInterstitialWorker().showAds();
                    if (showed) {
                        toAdsShowCounter = r;
                    } else {
                        toAdsShowCounter = 0;
                    }
                }
            },300);
            return;
        }
        toAdsShowCounter--;
    }

    public void showFragment(Class<?> clazz, boolean addToBackStack, BaseFragment fragment, View view) {
        FragmentTransaction fragmentTranaction = fragmentManager.beginTransaction();

        setFragmentShowAnimations(fragmentTranaction, addToBackStack, fragment, view);

        if (currentFragment==null) {
            fragmentTranaction.add(R.id.mainContainer, fragment);
        } else {
            fragmentTranaction.replace(R.id.mainContainer, fragment);
            if (addToBackStack) {
                fragmentTranaction.addToBackStack(clazz.getName());
            }
        }
        currentFragment = clazz;
        fragmentTranaction.commit();
        prepareAds();
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
        if (fragmentManager.getFragments().size()>0) {
            Fragment fragment = getCurrentFragment();
            currentFragment = fragment.getClass();
            prepareAds();
        } else {
            currentFragment = null;
        }
    }

    public void onActivityDestroy() {
        currentFragment = null;
    }
}
