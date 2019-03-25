package com.moon_rock.performancesdk;

import com.moon_rock.performancesdk.contract.IStartupTimeService;
import com.moon_rock.performancesdk.startupTimeService.StartupTimeParam;

class StartupTimeService implements IStartupTimeService {

    public interface IRecordStartupTime {
        void doRecord(StartupTimeParam startupTimeParam);
    }

    //超过五分钟的启动时间可以忽略不计
    public static final long STARTUP_TIME_THRESDHOLD = 5 * 60 * 1000;

    private boolean mColdStartupTimeInit = false;

    private long appLaunchTime;
    private long pageLaunchTime;

    private IRecordStartupTime mRecordStartupTime;
    private final StartupTimeParam startupTimeParam = new StartupTimeParam();

    @Override
    public void initColdStartupTime() {
        mColdStartupTimeInit = true;
    }

    @Override
    public boolean hasColdStartupTimeInit() {
        return mColdStartupTimeInit;
    }

    @Override
    public void startHotStartup() {
        appLaunchTime = System.currentTimeMillis();
    }

    @Override
    public void endHotStartup() {
        if (appLaunchTime > 0) {
            startupTimeParam.setHotStartupTime(System.currentTimeMillis() - appLaunchTime);
            appLaunchTime = 0;
        }
    }

    @Override
    public void startColdStartup() {
        if (pageLaunchTime == 0) {
            pageLaunchTime = System.currentTimeMillis();
        }
    }

    @Override
    public void recordStartupTime() {
        endColdStartup();
        endHotStartup();
        doRecordStartupTime();
    }

    @Override
    public boolean hasStartupTimeInfo() {
        return startupTimeParam.getHotStartupTime() > 0 || startupTimeParam.getColdStartupTime() > 0;
    }

    public void setRecordStartupTime(IRecordStartupTime recordStartupTime) {
        this.mRecordStartupTime = recordStartupTime;
    }

    private void endColdStartup() {
        if (pageLaunchTime > 0) {
            startupTimeParam.setColdStartupTime(System.currentTimeMillis() - pageLaunchTime);
            pageLaunchTime = 0;
        }
    }

    private void doRecordStartupTime() {

        if (mRecordStartupTime != null) {
            mRecordStartupTime.doRecord(startupTimeParam);
        }

        clearStartupTime();
    }


    private void clearStartupTime() {
        mColdStartupTimeInit = false;
        appLaunchTime = 0;
        pageLaunchTime = 0;
        startupTimeParam.clearStartupTime();
    }
}
