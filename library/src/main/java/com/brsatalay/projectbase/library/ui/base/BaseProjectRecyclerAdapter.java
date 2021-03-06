package com.brsatalay.projectbase.library.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brsatalay.projectbase.library.core.data.interfaces.AdapterListener;
import com.brsatalay.projectbase.library.core.data.model.ImageDisplayStyle;
import com.brsatalay.projectbase.library.core.util.UtilsImage;
import com.bumptech.glide.RequestManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barisatalay on 22.03.2018.
 */

public abstract class BaseProjectRecyclerAdapter<H extends BaseProjectHolder<M>, M> extends RecyclerView.Adapter<H> implements BaseProjectHolder.HolderListener {
    private String TAG = this.getClass().getSimpleName();
    private AdapterListener listener;
    private int selectableCount = 999;
    private List<M> cacheData;
    private List<M> allData;
    private List<M> selectedData;
    private Picasso picasso;
    private RequestManager glide;
    protected final int VIEW_TYPE_ITEM = 0;
    protected final int VIEW_TYPE_LOADING = 1;

    public BaseProjectRecyclerAdapter(List<M> cacheData) {
        this.cacheData = new ArrayList<>();
        this.allData = new ArrayList<>();
        this.selectedData = new ArrayList<>();
        if(cacheData != null) {
            addAllData(cacheData);
        }
    }

    @Override
    public H onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutResource(), viewGroup, false);
        return onCustomCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.setListener(this);
        onCustomBindViewHolder(holder, allData.get(position), position);


        applyListener(holder, position);
    }

    /**
     * Üst sınıfta itemView clickListeneri override yapılarak kullanılabilir.
     * */
    protected void applyListener(H holder, int position) {
        if(!isMainListenerDisable()) {
            holder.itemView.setOnClickListener(view -> {
                selectEvent(position);
                if (getListener() != null)
                    getListener().onItemClick(view, position);
            });
        }
    }

    protected void selectEvent(int position) {
        if (isSelectable()){
            M model = getAllData().get(position);
            if (!getSelectedData().contains(model)){
                if (selectableCount() <= getSelectedData().size()){
                    if (getSelectedData().size() > 0)
                        getSelectedData().remove(getSelectedData().size() - 1);
                }
                getSelectedData().add(model);
            }else{
                getSelectedData().remove(model);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return (null != allData ? allData.size() : 0);
    }

    public AdapterListener getListener() {
        return listener;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    public List<M> getCacheData() {
        if(cacheData == null)
            cacheData = new ArrayList<>();
        return cacheData;
    }

    public List<M> getAllData() {
        if(allData == null)
            allData = new ArrayList<>();
        return allData;
    }

    public void clearAllData(){
        allData.clear();
        cacheData.clear();
    }

    public void addAllData(List<M> cacheData) {
        this.allData.addAll(cacheData);
        this.cacheData.addAll(cacheData);
    }

    public void setAllData(List<M> cacheData){
        clearAllData();
        addAllData(cacheData);
    }

    public M getItemFromPosition(int position) {
        if (getAllData() == null || getAllData().size() == 0) return null;
        return getAllData().get(position);
    }

    public void changeData(int position, M data) {
        try {
            if (cacheData.size() != allData.size())
                throw new Throwable("Filtre işlemi varsa iptal edilmeli!");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        cacheData.set(position, data);
        allData.set(position, data);
    }

    public RequestManager getGlide() {
        return glide;
    }

    public void setGlide(RequestManager glide) {
        this.glide = glide;
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public void setPicasso(Picasso picasso) {
        this.picasso = picasso;
    }

    public void changeOrder() {
        List<M> newList = new ArrayList<>();

        for(int i=allData.size()-1; i>=0;i--)
            newList.add(allData.get(i));

        setAllData(newList);
    }

    public void removeItem(int position) {
        cacheData.remove(position);
        allData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cacheData.size());
    }

    public void insertItem(M newItem) {
        cacheData.add(newItem);
        allData.add(newItem);
        notifyItemInserted(allData.size() - 1);
//        notifyItemChanged(allData.size() - 1);
    }

    @Override
    public void onClickListener(View view, int position) {}

    @Override
    public void onLoadImage(String url, ImageView photoView, Callback callback, ImageDisplayStyle displayStyle) {
        if (getPicasso() != null)
            UtilsImage.loadImage(getPicasso(), url, photoView, callback, displayStyle);
        else if (getGlide() != null){
            UtilsImage.loadImageGlide(getGlide(), url, photoView, false, displayStyle);
        }else{
            throw new RuntimeException("Oluşturulan adaptöre glide veya picasso tanımlanmamış.");
        }
    }

    @Override
    public boolean isSelected(Object model) {
        return getSelectedData().contains(model);
    }

    public int selectableCount(){
        return selectableCount;
    }

    public void clearAllSelected() {
        getSelectedData().clear();
    }

    //region ..:: Abstract Defination ::..
    /**
     * Ekrana çizdirilecek satırın layoutunu buradan alır.
     * */
    public abstract int getLayoutResource();
    /**
     * Ekranda gözüken satırın bilgilerini parametre olarak döner
     * */
    public abstract void onCustomBindViewHolder(H holder, M model, int position);
    /**
     * View Holder sınıfı bir üst katmanda oluşturulup bu sınıfa döndürür
     * */
    public abstract H onCustomCreateViewHolder(View view);
    /**
     * Eğer bu sınıftaki click listener kullanmak istenilmiyorsa true dönmelidir.
     * */
    public abstract boolean isMainListenerDisable();
    /**
     * Adapterin satırlarının seçilebilir olması isteniyorsa burası true olmalı.
     * */
    public abstract boolean isSelectable();

    public List<M> getSelectedData() {
        return selectedData;
    }

    public void setSelectedData(List<M> selectedData) {
        this.selectedData = selectedData;
    }

    public int getSelectableCount() {
        return selectableCount;
    }

    public void setSelectableCount(int selectableCount) {
        this.selectableCount = selectableCount;
    }



    //endregion
}
