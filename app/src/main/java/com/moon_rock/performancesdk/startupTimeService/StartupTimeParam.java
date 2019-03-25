package com.moon_rock.performancesdk.startupTimeService;

public class StartupTimeParam {

    private long mHotStartupTime;
    private long mColdStartupTime;

    public long getHotStartupTime() {
        return mHotStartupTime;
    }

    public void setHotStartupTime(long hotStartupTime) {
        this.mHotStartupTime = hotStartupTime;
    }

    public long getColdStartupTime() {
        return mColdStartupTime;
    }

    public void setColdStartupTime(long coldStartupTime) {
        this.mColdStartupTime = coldStartupTime;
    }

    public void clearStartupTime() {
        mHotStartupTime = 0;
        mColdStartupTime = 0;
    }
}
