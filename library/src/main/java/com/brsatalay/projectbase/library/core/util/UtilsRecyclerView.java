package com.brsatalay.projectbase.library.core.util;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.brsatalay.projectbase.library.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;


/**
 * Created by Barış ATALAY on 24.11.2017.
 */

public class UtilsRecyclerView {
    /**
     * Yatay olarak listeme olayına snap desteği sağlar
     */
    public static void preparSnapHorizontalManRecycler(RecyclerView view) {
        if (view == null) return;
        LinearLayoutManager layoutManagerStart
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        view.setLayoutManager(layoutManagerStart);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(view);
    }
    /**
     * Dikey olarak listeme olayına snap desteği sağlar
     */
    public static void preparSnapVerticalManRecycler(RecyclerView view) {

        LinearLayoutManager layoutManagerStart
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        view.setLayoutManager(layoutManagerStart);
        view.setOnFlingListener(null);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(view);
    }
    /**
     * Grid mantığına göre satırları listeler
     */
    public static void prepareGridManRecycler(RecyclerView view) {
        prepareGridManRecycler(view, 3);
    }

    /**
     * Grid mantığına göre satırları listeler
     */
    public static void prepareGridManRecycler(RecyclerView view, int columCount) {
        if (view == null) return;
        GridLayoutManager manager = new GridLayoutManager(view.getContext(), columCount);
        manager.scrollToPosition(0);
        view.setLayoutManager(manager);

        view.setHasFixedSize(true);
    }

    public static void prepareVerticalManRecycler(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public static void prepareFlexboxManRecycler(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(recyclerView.getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
    }
}
