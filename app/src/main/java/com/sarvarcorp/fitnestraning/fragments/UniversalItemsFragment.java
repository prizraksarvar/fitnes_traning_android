package com.sarvarcorp.fitnestraning.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.listadapter.UniversalItemRecyclerViewAdapter;
import com.sarvarcorp.fitnestraning.models.UniversalItemsViewModel;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class UniversalItemsFragment extends BaseFragment implements UniversalItemRecyclerViewAdapter.UniversalItemListListener {
    private UniversalItemsViewModel viewModel;
    private RecyclerView mItemsListView;
    private UniversalItemRecyclerViewAdapter mRecyclerViewAdepter;
    private UniversalItem currentItem;

    private ConstraintLayout layout;
    private TextView titleTextView;
    private ImageView imageView;


    public UniversalItemsFragment() {

    }

    @Override
    protected int getLayoutID() {
        int layoutID = 0;
        String viewType = "";
        if (currentItem != null) {
            viewType = currentItem.viewType;
        }

        switch (viewType) {
            case "list_with_image": layoutID = R.layout.ui_fragment_image_title_list; break;
            case "list_with_image_title": layoutID = R.layout.ui_fragment_image_title_list; break;
            case "cart": layoutID = R.layout.ui_fragment_title_list; break;
            default: layoutID = R.layout.ui_fragment_title_list;
        }

        return layoutID;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UniversalItemsViewModel.class);
        viewModel.init(currentItem != null ? currentItem.id : 0);
    }

    public UniversalItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(UniversalItem currentItem) {
        this.currentItem = currentItem;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView(view);

        initViews(view);

        setTitle(currentItem !=null ? currentItem.name : "");
        if (currentItem !=null) {
            ViewCompat.setTransitionName(titleTextView, getTitleSharedName());
            ViewCompat.setTransitionName(layout, getLayoutSharedName());
            setBackgroundColor();
        }
        return view;
    }

    protected void initViews(View view) {
        layout = view.findViewById(R.id.universalItemsFragmentLayout);
        titleTextView = view.findViewById(R.id.universalItemTitleTextView);
        imageView = view.findViewById(R.id.universalItemImageView);
    }

    protected void setTitle(String titleString) {
        if (titleTextView == null)
            return;
        titleTextView.setText(titleString);
    }

    protected void setImage(String image) {
        if (imageView == null || image.equals(""))
            return;

        Glide.with(App.getComponent().provideStaticData().getMainActivity())
                .load(image)
                .into(imageView);
    }

    protected void setBackgroundColor() {
        if (currentItem.backgroundColor != null && !currentItem.backgroundColor.equals("")) {
            int color = Color.parseColor(currentItem.backgroundColor);
            color = Color.argb(Color.alpha(color),Color.red(color),Color.green(color),Color.blue(color));
            layout.setBackgroundColor(color);
        }
    }

    protected void initRecyclerView(View view) {
        mItemsListView = view.findViewById(R.id.universalItemsRecyclerView);
        mRecyclerViewAdepter = new UniversalItemRecyclerViewAdapter(this,this);
        mItemsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //mItemsListView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mItemsListView.setAdapter(mRecyclerViewAdepter);
        mRecyclerViewAdepter.setValues(viewModel.getList());
    }

    @Override
    public void onClickListItem(View view, UniversalItem universalItem) {
        if (universalItem!=null && universalItem.id>0) {
            try {
                UniversalItemsFragment fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
                fragment.setCurrentItem(universalItem);
                App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class,true, fragment, view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        if (currentItem ==null || view==null)
            return;
        ConstraintLayout layout = view.findViewById(R.id.universalItemButton);
        TextView title = view.findViewById(R.id.universalItemTitleTextView);
        ImageView imageView = view.findViewById(R.id.universalItemImageView);
        fragmentTranaction.addSharedElement(title,getTitleSharedName());
        fragmentTranaction.addSharedElement(layout,getLayoutSharedName());
        if (imageView!=null && !false) {
            fragmentTranaction.addSharedElement(imageView,getImageSharedName());
        }
        setSharedElementEnterTransition(new ButtonToFragmentTransition());
        setEnterTransition(new ChangeBounds());
        setExitTransition(new Fade());
        setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    private String getTitleSharedName() {
        return "universalItemTitle"+ currentItem.id;
    }

    private String getLayoutSharedName() {
        return "universalItemLayout"+ currentItem.id;
    }

    private String getImageSharedName() {
        return "universalItemImage"+ currentItem.id;
    }

    protected Mobileapp getMobileapp() {
        return App.getComponent().provideStaticData().getMobileapp();
    }
}
