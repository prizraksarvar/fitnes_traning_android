package com.cooldevs.exercisesflexibility.fragments.universalitem;

import android.view.View;
import android.widget.ImageView;

import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;

public class TitleImageListUniversalItemFragment extends TitleListUniversalItemFragment {
    protected ImageView imageView;
    @Override
    public int getLayoutID() {
        return R.layout.ui_fragment_image_title_list;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        imageView = view.findViewById(R.id.universalItemImageView);
    }

    @Override
    public void onCreateView(View view) {
        super.onCreateView(view);
        setImage(currentItem !=null ? currentItem.image : "");
    }

    protected void setImage(String image) {
        setImageToView(imageView,image);
    }

    @Override
    protected void setSharedTransitions() {
        super.setSharedTransitions();
        ViewCompat.setTransitionName(imageView, getImageSharedName());
    }

    private String getImageSharedName() {
        return "universalItemImage"+ getSharedNameID();
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        ImageView image = view.findViewById(R.id.universalItemImageView);
        if (image!=null) {
            fragmentTranaction.addSharedElement(image,getImageSharedName());
        }
        super.prepareEnterAnimation(fragmentTranaction, oldFragment, view);
    }
}
