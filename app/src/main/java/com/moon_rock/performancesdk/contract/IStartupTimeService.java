package com.moon_rock.performancesdk.contract;

import android.content.Context;

public interface IStartupTimeService extends IPerformanceMonitor {

    interface IStartupTimeContext {
    }

    void initColdStartupTime();

    boolean hasColdStartupTimeInit();

    /**
     * call at {@link android.app.Application#attachBaseContext(Context)}
     */
    void startHotStartup();

    void endHotStartup();

    void startColdStartup();

    /**
     * call at {@link android.app.Activity#onWindowFocusChanged(boolean) of the last startup activity}
     */
    void recordStartupTime();

    boolean hasStartupTimeInfo();
}
