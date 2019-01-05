package com.cooldevs.exercisesflexibility.listadapter.universalitem;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.listadapter.UniversalItemRecyclerViewAdapter;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseListViewHolder extends RecyclerView.ViewHolder {
    protected final View view;
    protected final ViewGroup containerView;
    protected UniversalItem universalItem;
    protected BaseFragment fragment;

    public BaseListViewHolder(View itemView, BaseFragment fragment, final UniversalItemRecyclerViewAdapter.UniversalItemListListener listener) {
        super(itemView);
        this.fragment = fragment;
        view = itemView;
        containerView = itemView.findViewById(R.id.universalItemButton);

        containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListItem(itemView, universalItem);
            }
        });
    }

    @Override
    public String toString() {
        return super.toString() + " '" + universalItem.id + "'";
    }

    public void update() {
        ViewCompat.setTransitionName(containerView,"universalItemLayout"+ universalItem.id);
        setBackgroundColor();
    }

    protected void setBackgroundColor() {
        if (!universalItem.backgroundColor.equals("")) {
            containerView.setBackgroundColor(Color.parseColor(universalItem.backgroundColor));
        } else {
            //containerView.setBackgroundColor(Color.parseColor("#5a595b"));
        }
    }

    public void setUniversalItem(UniversalItem universalItem) {
        this.universalItem = universalItem;
        update();
    }

    protected void setImageToView(ImageView imageView, String image, String transitionName) {
        if (!image.equals("") || imageView == null)
            return;

        Glide.with(fragment.getContext())
                .load(image)
                .into(imageView);
        ViewCompat.setTransitionName(imageView,transitionName+ universalItem.id);
    }
}
