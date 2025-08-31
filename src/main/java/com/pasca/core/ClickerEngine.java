package com.pasca.core;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class ClickerEngine {
    private Robot robot;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> currentTask;
    private ClickerSettings settings;
    private volatile boolean isClicking = false;
    private volatile boolean isEnabled = false;
    
    public ClickerEngine() throws AWTException {
        this.robot = new Robot();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.settings = new ClickerSettings();
    }
    
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        if (!enabled) {
            stopClicking();
        }
    }
    
    public boolean isEnabled() {
        return isEnabled;
    }
    
    public void startClicking() {
        if (!isEnabled || isClicking) {
            return;
        }
        
        isClicking = true;
        scheduleNextClick();
    }
    
    public void stopClicking() {
        isClicking = false;
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel(false);
        }
    }
    
    private void scheduleNextClick() {
        if (!isClicking || !isEnabled) {
            return;
        }
        
        long delay = calculateDelay();
        currentTask = scheduler.schedule(this::performClick, delay, TimeUnit.MILLISECONDS);
    }
    
    private void performClick() {
        if (!isClicking || !isEnabled) {
            return;
        }
        
        try {
            // Get current mouse position
            Point mousePos = MouseInfo.getPointerInfo().getLocation();
            
            // Add small random offset if jitter is enabled
            if (settings.isJitterEnabled()) {
                int jitterRange = settings.getJitterRange();
                int offsetX = ThreadLocalRandom.current().nextInt(-jitterRange, jitterRange + 1);
                int offsetY = ThreadLocalRandom.current().nextInt(-jitterRange, jitterRange + 1);
                
                robot.mouseMove(mousePos.x + offsetX, mousePos.y + offsetY);
                Thread.sleep(1); // Small delay
                robot.mouseMove(mousePos.x, mousePos.y); // Move back
            }
            
            // Perform click
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            
            // Schedule next click
            scheduleNextClick();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private long calculateDelay() {
        int minCps = settings.getMinCps();
        int maxCps = settings.getMaxCps();
        
        int minDelay = 1000 / maxCps;
        int maxDelay = 1000 / minCps;
        
        return ThreadLocalRandom.current().nextInt(minDelay, maxDelay + 1);
    }
    
    public void stop() {
        stopClicking();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
    
    public ClickerSettings getSettings() {
        return settings;
    }
}
