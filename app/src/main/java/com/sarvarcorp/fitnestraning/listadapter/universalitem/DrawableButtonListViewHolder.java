package com.sarvarcorp.fitnestraning.listadapter.universalitem;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.listadapter.UniversalItemRecyclerViewAdapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DrawableButtonListViewHolder extends BaseListViewHolder {
    private final TextView titleView;
    private ImageView imageView;
    private ImageView smallImageView;
    private ImageView statusImageView;
    private UniversalItem universalItem;

    public DrawableButtonListViewHolder(View itemView, final UniversalItemRecyclerViewAdapter.UniversalItemListListener listener) {
        super(itemView,listener);
        titleView = (TextView) itemView.findViewById(R.id.universalItemTitleTextView);
        titleView = (TextView) itemView.findViewById(R.id.universalItemTitleTextView);
        imageView = null;
        smallImageView = null;

        buttonView.setBackgroundColor(Color.parseColor("#5a595b"));

        buttonView.setOnClickListener(new View.OnClickListener() {
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
        titleView.setText(universalItem.name);
        ViewCompat.setTransitionName(titleView,"universalItemTitle"+ universalItem.id);
        ViewCompat.setTransitionName(buttonView,"universalItemLayout"+ universalItem.id);

        setImage();
        setSmallImage();
        setBackgroundColor();
    }

    private void setImage() {
        if (imageView == null) {
            imageView = (ImageView) itemView.findViewById(R.id.universalItemImageView);
            if (imageView==null) {
                return;
            }
        }
        if (!universalItem.image.equals("")) {
            Glide.with(fragment.getContext())
                    .load(universalItem.image)
                    .into(imageView);
        }
        ViewCompat.setTransitionName(imageView,"universalItemImage"+ universalItem.id);
    }

    private void setSmallImage() {
        if (smallImageView == null) {
            smallImageView = (ImageView) view.findViewById(R.id.universalItemSmallImageView);
            if (smallImageView==null) {
                return;
            }
        }
        if (!universalItem.smallImage.equals("")) {
            Glide.with(App.getComponent().provideStaticData().getMainActivity())
                    .load(universalItem.smallImage)
                    .into(smallImageView);
        }
        ViewCompat.setTransitionName(smallImageView,"universalItemSmallImage"+ universalItem.id);
    }

    private void setBackgroundColor() {
        if (!universalItem.backgroundColor.equals("")) {
            buttonView.setBackgroundColor(Color.parseColor(universalItem.backgroundColor));
        }
    }

    public void setUniversalItem(UniversalItem universalItem, int position) {
        this.universalItem = universalItem;
        update();
    }
}
