package com.example.todo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadExecutor {
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static ThreadExecutor executor;
    private final Executor diskIO;

    private ThreadExecutor(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static ThreadExecutor getInstance() {
        if (executor == null) {
            synchronized (LOCK) {
                executor = new ThreadExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return executor;
    }

    public Executor diskIO() {
        return diskIO;
    }
}
