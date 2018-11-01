package com.brsatalay.projectbase.library.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.data.model.ImageDisplayStyle;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
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

    @SuppressLint("CheckResult")
    public static void loadImageGlide(RequestManager glide, String url, ImageView photoView, boolean isThisLocalImage, ImageDisplayStyle displayStyle){
        /**
         * Not: Disk önbelleklemesi yaparken;
         *
         *      DiskCacheStrategy.DATA seçilirse: gelen resmin gerçek boyutunu önbelleğe alır.
         *      DiskCacheStrategy.RESOURCE seçilirse: eğer resmi işleme sokarsanız resize vs. işlemden sonraki halini önbelleğe alır.
         *      DiskCacheStrategy.AUTOMATIC  seçilirse: kendi mantıklı buldugu yöntemi seçer.
         *      DiskCacheStrategy.AUTOMATIC  seçilirse: görüntünün hem işlenmiş hemde işlenmemiz hali gibi bütün sürümlerini önbelleğe alır.
         * */
        RequestOptions requestOptions = new RequestOptions();
        if (displayStyle == ImageDisplayStyle.MEMORY) {
            //NOTHING :)
        }else if (displayStyle == ImageDisplayStyle.DISC)
            requestOptions.skipMemoryCache(true);
        else if (displayStyle == ImageDisplayStyle.NETWORK) {
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }else if (displayStyle == ImageDisplayStyle.CACHEONLY)
            requestOptions.onlyRetrieveFromCache(true);

        RequestBuilder<Drawable> builder = null;

        if (isThisLocalImage)
            builder = glide.load(new File(Uri.parse(url).getPath())); // Uri of the picture
        else
            builder = glide.load(url);

        builder.apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Log the GlideException here (locally or with a remote logging framework):
                        Log.e("UtilsImage$loadImage", "Load failed : " + e);

                        // You can also log the individual causes:
                        for (Throwable t : e.getRootCauses()) {
                            Log.e("UtilsImage$loadImage", "Caused by :" + t.getMessage());
                        }
                        // Or, to log all root causes locally, you can use the built in helper method:
                        e.logRootCauses("UtilsImage$loadImage");

                        return false; // Allow calling onLoadFailed on the Target.
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Log successes here or use DataSource to keep track of cache hits and misses.

                        return false; // Allow calling onResourceReady on the Target.
                    }
                })
                .into(photoView);
    }



}
