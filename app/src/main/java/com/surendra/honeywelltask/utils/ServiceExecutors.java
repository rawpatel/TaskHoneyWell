package com.surendra.honeywelltask.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServiceExecutors {
    private static final Object LOCK = new Object();
    private static ServiceExecutors instance;

    private final Executor networkIO;
    private final Executor mainThread;

    private ServiceExecutors(Executor networkIO, Executor mainThread) {
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static ServiceExecutors getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                instance = new ServiceExecutors(
                        Executors.newSingleThreadExecutor(),
                        new Executor() {
                            private final Handler handler = new Handler(Looper.getMainLooper());
                            @Override
                            public void execute(Runnable command) {
                                handler.post(command);
                            }
                        }
                );
            }
        }
        return instance;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }
}