package com.brsatalay.projectbase.library.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.data.model.mdlDialog;
import com.brsatalay.projectbase.library.ui.base.BaseActivity;

public class DialogActivity extends BaseActivity {
    private mdlDialog dialog = new mdlDialog();

    private TextView btnTitle;
    private TextView description;
    private TextView title;

    public static Intent newInstance(Context mContext, String title, String description, String buttonTitle, boolean isHtml){
        Intent intent = new Intent(mContext, DialogActivity.class);
        intent.putExtra("DialogTitle",title);
        intent.putExtra("DialogDescription", description);
        intent.putExtra("DialogButtonTitle", buttonTitle);
        intent.putExtra("isHtml", isHtml);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

        return intent;
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initUi() {
        btnTitle = findViewById(R.id.btnTitle);
        description = findViewById(R.id.description);
        title = findViewById(R.id.title);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onAsynchronousLoad() {
        catchExtras();

        getActivity().runOnUiThread(() -> {
            title.setText(dialog.getTitle());
            description.setText(dialog.getDescription());
            btnTitle.setText(dialog.getButtonTitle());
        });
    }

    @Override
    public void prepareFabricCrashlytics() {

    }

    private void catchExtras() {
        if(getIntent().hasExtra("isHtml")){
            dialog.setHtml(getIntent().getExtras().getBoolean("isHtml", false));
        }

        if(getIntent().hasExtra("DialogTitle")){
            dialog.setTitle(getIntent().getExtras().getString("DialogTitle", ""));
        }

        if(getIntent().hasExtra("DialogDescription")){
            dialog.setDescription(getIntent().getExtras().getString("DialogDescription", ""));
        }

        if(getIntent().hasExtra("DialogButtonTitle")){
            dialog.setButtonTitle(getIntent().getExtras().getString("DialogButtonTitle", ""));
        }
    }

    public void onOkey(View view){
        finish();
    }

    @Override
    public void onBackPressed(){}
}
