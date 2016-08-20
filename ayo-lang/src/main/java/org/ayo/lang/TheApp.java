package org.ayo.lang;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.ayo.Ayo;

import java.util.List;

/**
 * access informaions of this app
 *
 * Created by cowthan on 2016/1/24.
 */
public class TheApp {
    public static String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager pm = Ayo.context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(Ayo.context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageManager pm = Ayo.context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(Ayo.context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     *
     * @param cls
     * @return
     */
    public static boolean isServiceRunning(Class<?> cls) {
        ActivityManager am = (ActivityManager) Ayo.context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : infos) {
            String service = info.service.getClassName();
            if (service.equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前进程的名字，名字类似于：
     * com.ayo.daogou
     * com.ayo.daogou:remote
     *
     * 同一个app可以起多个进程，但是每个进程都有一个虚拟机，并都会启动一次application，所以application里的东西，
     * 最好保证只初始化一次
     *
     * 如果你有一个Activity需要运行在单独进程里，则默认进程里的单例，静态变量等都没法共享了，所以你可以单独给这个
     * 进程初始化一份application里的数据
     *
     * 归根结底，这方面的需求还是很少的
     *
     * 极光为什么要单独开个进程？
     * 融云的初始化官方已经建议判断当前进程了，只能在默认进程里初始化
     * 友盟更新相关的DownloadService单独开了一个进程
     *
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    public static String getChannelCode(Context context) {
        String code = getMetaData(context, "CHANNEL");
        if (code != null) {
            return code;
        }
        return "C_000";
    }

    /**
     *
     */
    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(
                            context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean checkPermission(Context context, String permission){
        PackageManager localPackageManager = context.getPackageManager();
        if (localPackageManager.checkPermission(permission,  //"android.permission.ACCESS_NETWORK_STATE"
                context.getPackageName()) != 0) {
            return true;
        }else{
            return false;
        }
    }

    public static String getVersion(Context appContext) {
        String version = "1.0.0";
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int getVersionCode(Context appContext) {
        int version = 1000;
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getPackageName(Context appContext) {
        String packageName = "";
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /*
     * require    <uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static boolean isMyPackageRunningOnTop(Context appContext) {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        if(am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
        if (infos != null && !infos.isEmpty()) {
            ActivityManager.RunningTaskInfo info = infos.get(0);
            ComponentName componentName = info.topActivity;
            if (componentName != null
                    && componentName.getPackageName().equals(appContext.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
