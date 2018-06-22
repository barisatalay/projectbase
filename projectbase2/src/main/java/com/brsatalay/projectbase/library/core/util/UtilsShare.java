package com.brsatalay.projectbase.library.core.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;

/**
 * Created by barisatalay on 25.03.2018.
 */

public class UtilsShare {
    public static void sharePlainText(Activity mActivity, String text){
        ShareCompat.IntentBuilder
                .from(mActivity)
                .setText(text)
                // most general text sharing MIME type
                .setType("text/plain")

                .setChooserTitle("Seçiniz")
                .startChooser();
    }

    public static void shareImage(Activity mActivity, String filePath, String text){
        File imageFile = new File(filePath);

        if (!imageFile.exists()){
            Toast.makeText(mActivity, "Paylasılacak öğe oluşturulurken bir sorun çıktı!", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uriToImage = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName()+ ".core.provider" , imageFile);

        ShareCompat.IntentBuilder
                .from(mActivity)
                .setType("image/jpeg")
                .setText(text)
                .setStream(uriToImage)
//                .getIntent();
                .startChooser();
    }

    public static void openImage(Activity mActivity, String filePath){
        File imageFile = new File(filePath);

        if (!imageFile.exists()){
            Toast.makeText(mActivity, "Paylasılacak öğe oluşturulurken bir sorun çıktı!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uriToImage = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName()+ ".core.provider" , imageFile);

        intent.setDataAndType(uriToImage,"image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mActivity.startActivity(intent);
    }

    public static void shareMp3(Activity mActivity, int rawId) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, UtilsSettings.rawToFile(mActivity.getApplicationContext(), rawId));
        share.setType("audio/mp3");
        mActivity.startActivity(Intent.createChooser(share, "Paylaşılacak yeri seçiniz"));
    }

}
