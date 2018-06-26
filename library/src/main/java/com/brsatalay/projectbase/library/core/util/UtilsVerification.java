package com.brsatalay.projectbase.library.core.util;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class UtilsVerification {
    public static boolean isValidUrl(String url){
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
