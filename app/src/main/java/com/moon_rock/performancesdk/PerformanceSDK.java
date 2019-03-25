package com.moon_rock.performancesdk;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.moon_rock.performancesdk.contract.IPerformanceMonitor;
import com.moon_rock.performancesdk.contract.IStartupTimeService;
import com.moon_rock.performancesdk.manager.RunningStatusManager;

/**
 * call {@link IStartupTimeService#startColdStartup()} at {@link Application#onCreate()}
 */
public class PerformanceSDK {

    private static class FiledHolder {
        static final PerformanceSDK instance = new PerformanceSDK();
    }

    private static class StartupTimeServiceHolder {
        static final IStartupTimeService instance = new StartupTimeService();
    }

    private PerformanceSDK() {
    }

    public static PerformanceSDK getInstance() {
        return FiledHolder.instance;
    }

    public void init(@NonNull Application application) {

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                IStartupTimeService startupTime = (IStartupTimeService) PerformanceSDK.getInstance().getService(PerformanceContext.PerformanceType.STARTUP_TIME);
                startupTime.endHotStartup();
                if (!startupTime.hasColdStartupTimeInit() && activity instanceof IStartupTimeService.IStartupTimeContext) {
                    startupTime.initColdStartupTime();
                    startupTime.startColdStartup();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                IStartupTimeService startupTime = (IStartupTimeService) PerformanceSDK.getInstance().getService(PerformanceContext.PerformanceType.STARTUP_TIME);
                if (startupTime.hasStartupTimeInfo()) {
                    if (RunningStatusManager.isBackgroundRunning(activity)) {
                        // record the startup time if necessary
                        startupTime.recordStartupTime();
                    }
                }
            }

        });
    }

    public IPerformanceMonitor getService(PerformanceContext.PerformanceType performanceType) {
        if (performanceType == PerformanceContext.PerformanceType.STARTUP_TIME) {
            return StartupTimeServiceHolder.instance;
        }

        return null;
    }

}
