package com.sarvarcorp.fitnestraning.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.models.UniversalItemsViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class UniversalItemDescriptionFragment extends BaseFragment implements Observer<List<UniversalItem>> {
    private UniversalItemsViewModel viewModel;
    private UniversalItem parentItem;
    private LiveData<List<UniversalItem>> universalItemLive;
    private LinearLayout layout;


    public UniversalItemDescriptionFragment() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_description;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UniversalItemsViewModel.class);
        viewModel.init(parentItem != null ? parentItem.id : 0);
        universalItemLive = viewModel.getList();
        universalItemLive.observe(this,this);
    }

    public UniversalItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(UniversalItem parentItem) {
        this.parentItem = parentItem;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        layout = view.findViewById(R.id.descriptionContainerLayout);

        return view;
    }

    @Override
    public void onChanged(List<UniversalItem> universalItems) {
        layout.removeAllViews();
        for (UniversalItem item: universalItems) {
            //TODO: Дубли нужно избавиться
            if (false) {
                View view = getLayoutInflater().inflate(R.layout.description_item_title, layout);
                TextView title = view.findViewById(R.id.descriptionTitleTextView);
                //TODO: костыль от не знания как получить вставленный вью
                title.setId(R.id.descriptionItemLayout);
                title.setText(item.name);
            }
            if (!item.description.equals("")) {
                View view = getLayoutInflater().inflate(R.layout.description_item_text, layout);
                TextView title = view.findViewById(R.id.descriptionTextView);
                //TODO: костыль от не знания как получить вставленный вью
                title.setId(R.id.descriptionItemLayout);
                title.setText(item.description);
            }
            if (!item.image.equals("")) {
                View view = getLayoutInflater().inflate(R.layout.description_item_image, layout);
                ImageView imageView = view.findViewById(R.id.descriptionImageView);
                //TODO: костыль от не знания как получить вставленный вью
                imageView.setId(R.id.descriptionItemLayout);
                Glide.with(App.getComponent().provideStaticData().getMainActivity())
                        .load(item.image)
                        .into(imageView);
            }
        }
    }
}
