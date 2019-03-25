package com.moon_rock.performancesdk.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public class RunningStatusManager {

    public static boolean isBackgroundRunning(@NonNull Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false;
                    }
                }
            }
        }

        return true;

    }

}
