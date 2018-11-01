package com.brsatalay.projectbase.library.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brsatalay.projectbase.library.core.mvp.BasePresenter;

public abstract class BaseProjectFragment<T extends BasePresenter> extends Fragment{
    public static final String TAG = BaseProjectFragment.class.getSimpleName();
    private T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            onBundle(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(getContentView(), container, false);
        initUi(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCreateView();
    }

    public T getPresenter() {
        return presenter;
    }

    public BaseProjectFragment setPresenter(T presenter) {
        this.presenter = presenter;
        return this;
    }

    public abstract void onCreateView();
    public abstract @LayoutRes int getContentView();
    public abstract void initUi(View rootView);
    public abstract void onBundle(Bundle bundle);

}
