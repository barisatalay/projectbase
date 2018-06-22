package com.brsatalay.projectbase.library.ui.base;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.brsatalay.projectbase.library.core.data.model.ImageDisplayStyle;
import com.squareup.picasso.Callback;

/**
 * Created by barisatalay on 22.03.2018.
 */

public abstract class BaseProjectHolder<T> extends RecyclerView.ViewHolder {
    public String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Activity mActivity;
    private HolderListener listener;
    /**
     * İçerisinde herhangi birşey stringe çevrilip saklanabilir.
     * */
    private String tempData;

    public BaseProjectHolder(View itemView) {
        super(itemView);
        setContext(itemView.getContext());
    }

    private BaseProjectHolder setContext(Context mContext){
        this.mContext = mContext;

        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public BaseProjectHolder setActivity(Activity mActivity) {
        this.mActivity = mActivity;
        return this;
    }

    public String getString(int resourceId){
        return getContext().getString(resourceId);
    }

    public Activity getActivity() {
        return mActivity;
    }

    public HolderListener getListener() {
        return listener;
    }

    public void setListener(HolderListener listener) {
        this.listener = listener;
    }

    public void setTempData(String tempData) {
        this.tempData = tempData;
    }

    public String getTempData() {
        if (tempData == null)
            tempData = "";
        return tempData;
    }

    public interface HolderListener<M>{
        void onClickListener(View view, int position);
        void onLoadImage(String url, ImageView photoView, Callback callback, ImageDisplayStyle displayStyle);
        boolean isSelected(M model);
    }

    public abstract void bind(T model);
}
