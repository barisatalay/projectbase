package com.brsatalay.projectbase.library.core.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.brsatalay.projectbase.library.R;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.TransitionManager;


/**
 * Created by barisatalay on 23.03.2018.
 */

public class UtilsTransition {
    /**
     * Textview nesnesinin textini animasyonlu değiştirir
     * */
    public static void changeText(TextView view, String text){
        TransitionManager.beginDelayedTransition((ViewGroup) view.getRootView(),  new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN));
        view.setText(text);
    }

    /**
     * Hedef gösterilen viewGroup içerisindeki olayı otomatik transition olarak ayarlar
     * */
    public static void autoTransition(ViewGroup viewGroup){
        TransitionManager.beginDelayedTransition(viewGroup);
    }

    public static void changeColor(ViewGroup viewGroup) {
        TransitionManager.beginDelayedTransition(viewGroup, new Recolor());
    }

    public static void changeRotate(View view, int rotateValue) {
        TransitionManager.beginDelayedTransition((ViewGroup) view.getRootView(), new Rotate());
        view.setRotation(rotateValue);
    }

    public static void recyclerViewLoadAnimationFromBottom(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public static void changeTintColor(ImageView view, int colorValue) {
        TransitionManager.beginDelayedTransition((ViewGroup) view.getRootView(), new Recolor());
        view.setColorFilter(ContextCompat.getColor(view.getContext(), colorValue));
    }
}
