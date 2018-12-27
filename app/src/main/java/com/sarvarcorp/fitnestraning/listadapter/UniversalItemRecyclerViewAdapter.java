package com.sarvarcorp.fitnestraning.listadapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class UniversalItemRecyclerViewAdapter
        extends RecyclerView.Adapter<UniversalItemRecyclerViewAdapter.UniversalItemListViewHolder>
        implements Observer<List<UniversalItem>> {
    private LiveData<List<UniversalItem>> mValues;

    private UniversalItemListListener listener;
    private BaseFragment fragment;

    public UniversalItemRecyclerViewAdapter(UniversalItemListListener listener, BaseFragment fragment) {
        this.listener = listener;
        this.fragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        String viewType = mValues.getValue().get(position).viewType;
        int layout = R.layout.list_item_universal_with_image;
        switch (viewType) {
            case "button": layout = R.layout.list_item_universal_button; break;
            case "small_button": layout = R.layout.list_item_universal_small_button; break;
            case "small_button_with_image": layout = R.layout.list_item_universal_small_button_with_image; break;
            default: layout = R.layout.list_item_universal_with_image; break;
        }
        return layout;
    }

    @NonNull
    @Override
    public UniversalItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new UniversalItemListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversalItemListViewHolder holder, int position) {
        if (mValues==null || mValues.getValue()==null)
            return;
        holder.setUniversalItem(mValues.getValue().get(position),position);
    }

    public void setValues(LiveData<List<UniversalItem>> values) {
        mValues = values;
        mValues.observeForever(this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mValues!=null && mValues.getValue()!=null)
            return mValues.getValue().size();
        return 0;
    }

    @Override
    public void onChanged(@Nullable List<UniversalItem> products) {
        notifyDataSetChanged();
    }

    public class UniversalItemListViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ConstraintLayout buttonView;
        private final TextView titleView;
        private ImageView imageView;
        private ImageView smallImageView;
        private UniversalItem universalItem;

        public UniversalItemListViewHolder(View itemView, final UniversalItemListListener listener) {
            super(itemView);
            view = itemView;
            buttonView = (ConstraintLayout) itemView.findViewById(R.id.universalItemButton);
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

    public interface UniversalItemListListener {
        void onClickListItem(View view, UniversalItem universalItem);
    }
}
