package com.example.library.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.example.library.base.BaseApplication;

public class PermissionsUtils {

    private static Context mContext = null;

    public static void gotoPermission(){
        mContext = BaseApplication.getContext();
        if(RomUtils.isEmui()){
            gotoHuaweiPermission();
        }else if(RomUtils.isMiui()){
            gotoMiuiPermission();
        }else if(RomUtils.isFlyme()){
            gotoMeizuPermission();
        }else{
            gotoSystemPermission();
        }
        mContext = null;
    }



    // 华为的权限管理页面

    private static void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    //  跳转到系统的设置界面
    //获取应用详情页面intent

    private static void gotoSystemPermission() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        mContext.startActivity(localIntent);
    }


    // 跳转到魅族的权限管理系统

    private static void gotoMeizuPermission() {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName",  mContext.getPackageName());
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission();
        }
    }


    //跳转到miui的权限管理页面

    private static void gotoMiuiPermission() {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname",  mContext.getPackageName());
        try {
            mContext.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoSystemPermission();
        }
    }
}
