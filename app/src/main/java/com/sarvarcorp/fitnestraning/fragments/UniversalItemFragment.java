package com.sarvarcorp.fitnestraning.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.models.UniversalItemViewModel;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class UniversalItemFragment extends BaseFragment implements Observer<UniversalItem> {
    private UniversalItem universalItem;
    private UniversalItemViewModel viewModel;
    private LiveData<UniversalItem> universalItemLive;

    private TextView titelTextView;
    private ImageView imageView;
    private TextView descriptionTextView;
    private LinearLayout layout;

    public UniversalItem getUniversalItem() {
        return universalItem;
    }

    public void setUniversalItem(UniversalItem universalItem) {
        this.universalItem = universalItem;
    }

    public UniversalItemFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UniversalItemViewModel.class);
        viewModel.init(universalItem.id);
        universalItemLive = viewModel.get();

        universalItemLive.observe(this,this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        titelTextView = rootView.findViewById(R.id.universalItemTitleTextView);
        layout = rootView.findViewById(R.id.universalItemFragmentLayout2);
        imageView = rootView.findViewById(R.id.universalItemImageView);
        descriptionTextView = rootView.findViewById(R.id.universalItemDescription);

        if (universalItem!=null) {
            setBackgroundColor(universalItem);
            try {
                FragmentManager fragmentManager = App.getComponent().provideStaticData().getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                UniversalItemDescriptionFragment fragment = (UniversalItemDescriptionFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemDescriptionFragment.class);
                fragment.setParentItem(universalItem);
                FrameLayout descrLayout = rootView.findViewById(R.id.descrFragmentContainer);
                ConstraintSet set = new ConstraintSet();
                transaction.add(descrLayout.getId(),fragment);
                transaction.commit();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }

        ViewCompat.setTransitionName(titelTextView,getTitleSharedName());
        ViewCompat.setTransitionName(imageView, getImageSharedName());
        ViewCompat.setTransitionName(layout, getLayoutSharedName());
        return rootView;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_universal_item;
    }

    @Override
    public void onChanged(@Nullable UniversalItem universalItem) {
        if (universalItem!=null) {
            titelTextView.setText(universalItem.name);
            descriptionTextView.setText(universalItem.description);

            if (!universalItem.image.equals("")) {
                Glide.with(App.getComponent().provideStaticData().getMainActivity())
                        .load(universalItem.image)
                        .into(imageView);
                //setImageView(universalItem.image, imageView);
            }

            setBackgroundColor(universalItem);

        }
    }

    private void setBackgroundColor(UniversalItem universalItem) {
        if (!universalItem.backgroundColor.equals("")) {
            int color = Color.parseColor(universalItem.backgroundColor);
            color = Color.argb(50,Color.red(color),Color.green(color),Color.blue(color));
            layout.setBackgroundColor(color);
        }
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        ConstraintLayout layout = view.findViewById(R.id.universalItemButton);
        ImageView image = view.findViewById(R.id.universalItemImageView);
        TextView title = view.findViewById(R.id.universalItemTitleTextView);
        fragmentTranaction.addSharedElement(layout,getLayoutSharedName());
        fragmentTranaction.addSharedElement(title,getTitleSharedName());
        if (image!=null)
            fragmentTranaction.addSharedElement(image, getImageSharedName());
        setSharedElementEnterTransition(new ButtonToFragmentTransition());
        setEnterTransition(new ChangeBounds());
        setExitTransition(new ChangeBounds());
        setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    private String getTitleSharedName() {
        return "universalItemTitle"+ universalItem.id;
    }

    private String getImageSharedName() {
        return "universalItemImage"+ universalItem.id;
    }

    private String getLayoutSharedName() {
        return "universalItemLayout"+ universalItem.id;
    }
}
