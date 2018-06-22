package com.brsatalay.projectbase.library.core.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.brsatalay.projectbase.library.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.AUDIO_SERVICE;

public class UtilsSettings {

    public static Uri rawToFile(Context mContext, int rawId){
        File dest = Environment.getExternalStorageDirectory();
        InputStream in = mContext.getResources().openRawResource(rawId);
        try{
            OutputStream out = new FileOutputStream(new File(dest, "lastUriFile.mp3"));
            byte[] buf = new byte[1024];
            int len;
            while ( (len = in.read(buf, 0, buf.length)) != -1){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
        catch (Exception e) {}

        return Uri.parse(Environment.getExternalStorageDirectory().toString() + "/lastUriFile.mp3");
    }

    public static void rawToRingTone(Context mContext, int rawId){
//        Uri path = rawToFile(mContext, rawId);
//        RingtoneManager.setActualDefaultRingtoneUri(
//                mContext, RingtoneManager.TYPE_RINGTONE,
//                path);
////        Log .i("TESTT", "Ringtone Set to Resource: "+ path.toString());
//        RingtoneManager.getRingtone(mContext, path)
//                .play();

//        String fPAth = "android.resource://" + mContext.getApplicationContext().getPackageName() + "/raw/kiss_me";

        AssetFileDescriptor openAssetFileDescriptor;
        ((AudioManager) mContext.getSystemService(AUDIO_SERVICE)).setRingerMode(2);
        File file = new File(Environment.getExternalStorageDirectory() + "/appkeeda", "ring.mp3");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri parse = Uri.parse("android.resource://" +
                mContext.getPackageName() + "/raw/" +
                rawId);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(parse, "r");
        } catch (FileNotFoundException e2) {
            openAssetFileDescriptor = null;
        }
        try {
            byte[] bArr = new byte[1024];
            FileInputStream createInputStream = openAssetFileDescriptor.createInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (int read = createInputStream.read(bArr); read != -1; read = createInputStream.read(bArr)) {
                fileOutputStream.write(bArr, 0, read);
            }
            fileOutputStream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file.getAbsolutePath());
        contentValues.put("title", "nkDroid ringtone");
        contentValues.put("mime_type", "audio/mp3");
        contentValues.put("_size", Long.valueOf(file.length()));
        contentValues.put("artist", Integer.valueOf(R.string.app_name));
        contentValues.put("is_ringtone", Boolean.valueOf(true));
        contentValues.put("is_notification", Boolean.valueOf(false));
        contentValues.put("is_alarm", Boolean.valueOf(false));
        contentValues.put("is_music", Boolean.valueOf(false));
        try {
//            Toast.makeText(this, new StringBuilder().append("Ringtone set successfully"), Toast.LENGTH_LONG).show();
            RingtoneManager.setActualDefaultRingtoneUri(mContext, 1, contentResolver.insert(MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath()), contentValues));
        } catch (Throwable th) {
//            Toast.makeText(this, new StringBuilder().append("Ringtone feature is not working"), Toast.LENGTH_LONG).show();
        }
    }
}
