package com.sarvarcorp.fitnestraning.listadapter.universalitem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.listadapter.UniversalItemRecyclerViewAdapter;

import androidx.core.view.ViewCompat;

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
