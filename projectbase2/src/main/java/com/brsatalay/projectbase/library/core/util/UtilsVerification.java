package com.brsatalay.projectbase.library.core.util;

import android.util.Patterns;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class UtilsVerification {
    public static boolean checkValid(String url){
        return Patterns.WEB_URL.matcher(url).matches();
    }
}
