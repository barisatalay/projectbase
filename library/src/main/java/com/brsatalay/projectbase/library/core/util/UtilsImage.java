package com.brsatalay.projectbase.library.core.util;

import android.content.Context;
import android.widget.ImageView;

import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.data.model.ImageDisplayStyle;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by barisatalay on 11.04.2018.
 */

public class UtilsImage {
    public static String saveBase64(Context mContext, String base64Image, String fileName, boolean override) {
        if(base64Image == null) return "";
        final byte[] imgBytesData = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);

        File file = null;
        file = new File(mContext.getDir("filesdir", Context.MODE_PRIVATE) + "/FacilityImages/");// File.createTempFile("FacilityImages", null, mContext.getCacheDir());
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        file = new File(file.getAbsolutePath() + "/" + fileName);

        if(!override)
            if(file.exists())
                return file.getAbsolutePath();

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file.getAbsolutePath();
    }

    public static void loadImage(Picasso picasso, String url, ImageView photoView, Callback callback, ImageDisplayStyle displayStyle){
        picasso.load(url)
                .placeholder(R.drawable.ic_no_photo)
                .error(R.drawable.ic_no_photo)
                .noFade()
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        RequestCreator build = picasso
                                .load(url);
                        if (displayStyle == ImageDisplayStyle.MEMORY) {
                            //NOTHING :)
                        }else if (displayStyle == ImageDisplayStyle.DISC)
                            build.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
                        else if (displayStyle == ImageDisplayStyle.NETWORK) {
                            build.memoryPolicy(MemoryPolicy.NO_CACHE);
                            build.networkPolicy(NetworkPolicy.NO_CACHE);
                        }else if (displayStyle == ImageDisplayStyle.CACHEONLY)
                            build.networkPolicy(NetworkPolicy.OFFLINE);

                        build.into(photoView, callback);
                    }

                    @Override
                    public void onError() {}
                });
    }

    public static void loadImage(Picasso picasso, int resourceId, ImageView photoView, Callback callback, ImageDisplayStyle displayStyle){
        picasso.load(resourceId)
                .placeholder(R.drawable.ic_no_photo)
                .error(R.drawable.ic_no_photo)
                .noFade()
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        RequestCreator build = picasso
                                .load(resourceId);
                        if (displayStyle == ImageDisplayStyle.MEMORY) {
                            //NOTHING :)
                        }else if (displayStyle == ImageDisplayStyle.DISC)
                            build.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
                        else if (displayStyle == ImageDisplayStyle.NETWORK) {
                            build.memoryPolicy(MemoryPolicy.NO_CACHE);
                            build.networkPolicy(NetworkPolicy.NO_CACHE);
                        }else if (displayStyle == ImageDisplayStyle.CACHEONLY)
                            build.networkPolicy(NetworkPolicy.OFFLINE);

                        build.into(photoView, callback);
                    }

                    @Override
                    public void onError() {}
                });
    }
}
