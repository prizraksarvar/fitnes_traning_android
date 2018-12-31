package com.sarvarcorp.fitnestraning.fragments.universalitem;

import android.view.View;
import android.widget.TextView;

import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;

public class TitleUniversalItemFragment extends BaseUniversalItemFragment {
    protected TextView titleTextView;
    @Override
    public int getLayoutID() {
        return R.layout.ui_fragment_title_list;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        titleTextView = view.findViewById(R.id.universalItemTitleTextView);
    }

    @Override
    public void onCreateView(View view) {
        super.onCreateView(view);
        setTitle(currentItem !=null ? currentItem.name : "");
    }

    protected void setTitle(String titleString) {
        if (titleTextView == null)
            return;
        titleTextView.setText(titleString);
    }

    @Override
    protected void setSharedTransitions() {
        super.setSharedTransitions();
        ViewCompat.setTransitionName(titleTextView, getTitleSharedName());
    }

    private String getTitleSharedName() {
        return "universalItemTitle"+ getSharedNameID();
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        TextView title = view.findViewById(R.id.universalItemTitleTextView);
        if (title!=null) {
            fragmentTranaction.addSharedElement(title,getTitleSharedName());
        }
        super.prepareEnterAnimation(fragmentTranaction, oldFragment, view);
    }
}
