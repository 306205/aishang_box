package com.done.player.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.done.player.app.BaseApp;

/**
 * Created by XDONE on 2017/12/28.
 */

public class PermissionUtil {

    public static String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE};

    public static boolean isLacksOfPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(
                    BaseApp.getInstance().getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED;
        }
        return false;
    }

}