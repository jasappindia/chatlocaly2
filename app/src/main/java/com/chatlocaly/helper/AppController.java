package com.chatlocaly.helper;

/**
 * Created by prateek on 6/5/16.
 */

import android.app.Application;
import android.util.Log;


import java.io.File;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "OswaldLight.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "OswaldLight.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "OswaldLight.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "OswaldLight.ttf");
        
    }


    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


}