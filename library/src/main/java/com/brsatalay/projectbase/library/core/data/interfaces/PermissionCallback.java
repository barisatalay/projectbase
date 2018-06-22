package com.brsatalay.projectbase.library.core.data.interfaces;

import com.karumi.dexter.MultiplePermissionsReport;

/**
 * Created by barisatalay on 26.03.2018.
 */

public interface PermissionCallback {
    void successful(MultiplePermissionsReport report);
    void denied(MultiplePermissionsReport report);
}
