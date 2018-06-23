package com.example.android.mood;

import android.os.Process;

public class CustomRunnable implements Runnable {

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
    }
}
