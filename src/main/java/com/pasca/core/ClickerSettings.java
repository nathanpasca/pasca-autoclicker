package com.pasca.core;

public class ClickerSettings {
    private int minCps = 8;
    private int maxCps = 12;
    private boolean jitterEnabled = false;
    private int jitterRange = 2;
    
    public int getMinCps() {
        return minCps;
    }
    
    public void setMinCps(int minCps) {
        if (minCps > 0 && minCps <= maxCps) {
            this.minCps = minCps;
        }
    }
    
    public int getMaxCps() {
        return maxCps;
    }
    
    public void setMaxCps(int maxCps) {
        if (maxCps >= minCps && maxCps <= 100) {
            this.maxCps = maxCps;
        }
    }
    
    public boolean isJitterEnabled() {
        return jitterEnabled;
    }
    
    public void setJitterEnabled(boolean jitterEnabled) {
        this.jitterEnabled = jitterEnabled;
    }
    
    public int getJitterRange() {
        return jitterRange;
    }
    
    public void setJitterRange(int jitterRange) {
        if (jitterRange >= 0 && jitterRange <= 10) {
            this.jitterRange = jitterRange;
        }
    }
}
