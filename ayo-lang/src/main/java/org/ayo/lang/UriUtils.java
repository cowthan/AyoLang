package org.ayo.lang;

import android.net.Uri;

import org.ayo.Ayo;

import java.io.File;

/**
 * Created by Administrator on 2017/1/14 0014.
 */

public class UriUtils {

    /**
     * 转成file://
     * @param localPath
     * @return
     */
    public static String parseAbsolutePathToUri(String localPath){
        return Uri.fromFile(new File(localPath)).toString();
    }

    public static String parseAssetsPathToUri(String assetsPath){
        return "file:///android_asset/" + assetsPath;
    }

    public static String parseResourceToUri(int resId){
        String s = "android.resource://{pkgName}/" + resId;
        Uri uri = Uri.parse(s.replace("{pkgName}", Lang.app.getPackageName()));
        return uri.toString();
    }
//    public static String parseContentToUri(String content){
//        String s = "android.resource://{pkgName}/" + resId;
//        Uri uri = Uri.parse(s.replace("{pkgName}", VanGogh.app.getPackageName()));
//        return uri.toString();
//    }
}
