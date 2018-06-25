package com.brsatalay.projectbase.library.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.util.UtilsKeyboard;

public abstract class BaseProjectDrawerActivity extends BaseProjectActivity implements DrawerLayout.DrawerListener{
    private DrawerLayout drawerLayout;
    private boolean isLeftDrawerInfoLoaded;

    private NavigationView navigationView;
    private NavigationView navigationView_Right;

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle bundle){
        super.setIsDrawerActivity();
        super.onCreate(bundle);
        setContentView(R.layout.activity_drawer_base);
        initView();
        this.isLeftDrawerInfoLoaded = false;

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

    private void closeLeftDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    protected void closeRightDrawer() {
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
        loadLeftDrawerInfo();
    }

    protected void loadLeftDrawerInfo() {
//        if (isLeftDrawerInfoLoaded) return;
//
//        nav_view_logo = findViewById(R.id.nav_view_logo);
//        nav_view_guide_name = findViewById(R.id.nav_view_guide_name);
//        leftDrawerList = findViewById(R.id.leftDrawerList);
//        nav_view_version = findViewById(R.id.nav_view_version);
//
//        nav_view_version.setText( getString(R.string.appversion) + " " + UtilsGeneral.getAppVersionName(getApplicationContext()));
//
//        findViewById(R.id.nav_view_signout).setOnClickListener(this);
//        findViewById(R.id.nav_view_close_left).setOnClickListener(this);
//
//        loadGuideInfo();
//
//        DrawerAdapter adapter = new DrawerAdapter(SejourApplication.getInstance().getDrawerList());
//        adapter.setListener((view, position) -> {
//            int stringResource = SejourApplication.getInstance().getDrawerList().get(position).getText();
//            Intent activityIntent = null;
//            //ActivityOptions options =
//            //        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
//
//            UtilsFirebase.addClickAnalytics(this, String.valueOf(view.getId()),"Drawer Item","row");
//
//
//            switch (stringResource){
//                case R.string.nav_view_home: activityIntent = MainActivity.newInstance(getApplicationContext()); break;
//                case R.string.nav_view_toursale: activityIntent = TourSaleActivity.newInstance(getApplicationContext()); break;
//                case R.string.nav_view_summary: activityIntent = SaleReportActivity.newInstance(getApplicationContext()); break;
////                case R.string.nav_view_infcoctail: activityIntent = DownloadedActivity.newInstance(getApplicationContext()); break;
//                case R.string.nav_view_tourfacility: activityIntent = TourFacilityActivity.newInstance(getApplicationContext()); break;
//                case R.string.nav_view_notification: activityIntent = AnnouncementActivity.newInstance(getApplicationContext(), false); break;
//                case R.string.nav_view_settings: activityIntent = SettingActivity.newInstance(getApplicationContext());  break;
//            }
//            if (activityIntent != null) {
//                startActivity(activityIntent);
//                //overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
//            }
//
//        });
//
//        getActivity().runOnUiThread(() -> {
//            UtilsRecyclerView.prepareVerticalManRecycler(leftDrawerList);
//            leftDrawerList.setAdapter(adapter);
//        });
//
//        isLeftDrawerInfoLoaded = true;
    }

    private void loadGuideInfo() {
//        nav_view_guide_name.setText(SejourApplication.getInstance().getSessionManager().getActiveUser().getAdi());
//
//        mdlSejourParam sejourParam = new Select().from(mdlSejourParam.class).executeSingle();
//
//        if(sejourParam != null && sejourParam.getMobBiletLogo() != null && !sejourParam.getMobBiletLogo().isEmpty()){
//            String path = UtilsImage.saveBase64(getApplicationContext(), sejourParam.getMobBiletLogo(), "MobBiletLogo.png", false);
//
//            UtilsImage.loadImage(SejourApplication.getInstance().getPicasso(),"file://" + path, nav_view_logo, null, ImageDisplayStyle.CACHEONLY);
//        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.nav_view_signout:
//                SejourApplication.getInstance().clearUserDataForLogout();
//                startActivity(LoginActivity.newInstance(getApplicationContext()));
//                break;
//
//            case R.id.nav_view_close_left:
//                closeLeftDrawer();
//                break;
//        }
//    }

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
