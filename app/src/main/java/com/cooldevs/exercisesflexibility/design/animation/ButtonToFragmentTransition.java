package com.cooldevs.exercisesflexibility.design.animation;

import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;

public class ButtonToFragmentTransition extends TransitionSet {
    public ButtonToFragmentTransition() {
        setOrdering(ORDERING_TOGETHER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addTransition(new ChangeImageTransform());
        }
        addTransition(new ChangeBounds());

        addTransition(new ChangeColor());

        addTransition(new Fade());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addTransition(new ChangeTransform());
        }
    }
}