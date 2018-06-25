package com.brsatalay.projectbase.library.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.util.UtilsKeyboard;

public abstract class BaseProjectDrawerActivity extends BaseProjectActivity implements DrawerLayout.DrawerListener{
    private DrawerLayout drawerLayout;

    private NavigationView navigationView;
    private NavigationView navigationView_Right;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle bundle){
        super.setIsDrawerActivity();
        super.onCreate(bundle);
        setContentView(R.layout.activity_drawer_base);
        initView();

        closeLeftDrawer();

        lockAllDrawers();

        initUi();
        initPresenter();
    }

    public void initView() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView_Right = findViewById(R.id.nav_view_right);

        ViewStub stub = findViewById(R.id.stub);

        stub.setLayoutResource(getContentView());
        stub.inflate();

        applySelfClickListener();
    }

    private void applySelfClickListener() {
        View drawer = findViewById(R.id.toolbar_menu);
        if(drawer != null)
            drawer.setOnClickListener(view -> toggleLeftDrawer());

        setDrawerListener(this);
    }
    protected void setDrawerListener(@NonNull DrawerLayout.DrawerListener listener){
        drawerLayout.addDrawerListener(listener);
    }

    protected void toggleLeftDrawer() {
        toggleDrawer(GravityCompat.START);
    }

    protected void toggleRightDrawer() {
        toggleDrawer(GravityCompat.END);
    }

    private void toggleDrawer(int drawerType){
        if (drawerLayout.isDrawerOpen(drawerType)) {
            drawerLayout.closeDrawer(drawerType);
        } else {

            drawerLayout.openDrawer(drawerType);
        }
    }

    public void closeLeftDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void closeRightDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END))
            drawerLayout.closeDrawer(GravityCompat.END);
    }

    private void lockAllDrawers() {
        rightDrawerLock();
        leftDrawerLock();
    }

    /**
     * Sol menüyü kitler
     * */
    private void leftDrawerLock() {
        getActivity().runOnUiThread(() -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, navigationView));
    }
    /**
     * Sol menünün kilidini açar
     * */
    public void leftDrawerUnLock(){
        getActivity().runOnUiThread(() -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.nav_view) ));
    }

    /**
     * Sağ menünün içini temizleyip kullanımı kitleniyor.
     * */
    private void rightDrawerLock() {
        rightDrawerLock(true);
    }

    /**
     * Sağ menünün içini parametreye göre temizleyip kullanımı kitleniyor.
     * */
    public void rightDrawerLock(boolean removeAllElement) {
        getActivity().runOnUiThread(() -> {
            if(removeAllElement)
                navigationView_Right.removeAllViews();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, navigationView_Right);
        });
    }

    public void rightDrawerUnLock() {
        getActivity().runOnUiThread(() -> {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.nav_view_right));
        });
    }

    /**
     * Sağ menüdeki viewlere erişirken kullanılmalıdır.
     * */
    public NavigationView getRightDrawer(){
        return navigationView_Right;
    }

    public abstract void onCreateView();
    @Override
    public abstract @LayoutRes int getContentView();
    public abstract void initUi();
    public abstract void initPresenter();
    /** onCreateCustom işlemlerini bitirince işlemlerin devam edeceği farklı bir thread*/
    public abstract void onAsynchronousLoad();

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        UtilsKeyboard.hideSoftKeyboard(getActivity());
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        UtilsKeyboard.hideSoftKeyboard(getActivity());
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    public void loadRightDrawer(int drawerLayoutResId) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.nav_view_right) );
        LinearLayout inflatedLayout = new LinearLayout(getActivity());
        LayoutInflater.from(getActivity()).inflate(drawerLayoutResId, inflatedLayout, true);

        navigationView_Right.addView(inflatedLayout);
    }

    public void loadLeftDrawer(int drawerLayoutResId) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.nav_view) );
        LinearLayout inflatedLayout = new LinearLayout(getActivity());
        LayoutInflater.from(getActivity()).inflate(drawerLayoutResId, inflatedLayout, true);

        navigationView.addView(inflatedLayout);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }

            this.doubleBackToExitPressedOnce = true;
            showToast(R.string.forexit);

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }

}
