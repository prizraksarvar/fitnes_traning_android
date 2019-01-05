package com.cooldevs.exercisesflexibility.listadapter.universalitem;

import android.view.View;
import android.widget.ImageView;

import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.listadapter.UniversalItemRecyclerViewAdapter;

public class DrawableButtonImageListViewHolder extends DrawableButtonListViewHolder {
    protected ImageView statusImageView;

    public DrawableButtonImageListViewHolder(View itemView, BaseFragment fragment, final UniversalItemRecyclerViewAdapter.UniversalItemListListener listener) {
        super(itemView, fragment, listener);
        statusImageView = itemView.findViewById(R.id.uiItemStatusImage);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + universalItem.id + "'";
    }

    @Override
    public void update() {
        super.update();
        setStatusImage();
    }

    protected void setStatusImage() {
        setImageToView(statusImageView,universalItem.smallImage,"universalItemStatusImage");
    }
}
