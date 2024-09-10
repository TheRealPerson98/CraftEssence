package com.person98.commonsessence.scheduler;

public interface EssenceTask {
    void cancel();   // Cancel the task
    boolean isCancelled();  // Check if the task is already cancelled
}
