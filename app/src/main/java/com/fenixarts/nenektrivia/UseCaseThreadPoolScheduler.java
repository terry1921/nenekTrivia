package com.fenixarts.nenektrivia;

import android.os.Handler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * QuinielaPicante
 * Created by terry0022 on 21/09/17 - 16:46.
 */

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    private final Handler mHandler = new Handler();

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private static final long TIMEOUT = 60;

    private final ThreadPoolExecutor mThreadPoolExecutor;

    UseCaseThreadPoolScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2, TIMEOUT, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends UseCase.ResponseValues> void notifyResponse(final V response, final UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(() -> useCaseCallback.onSuccess(response));
    }

    @Override
    public <V extends UseCase.ResponseValues> void onError(final UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(useCaseCallback::onError);
    }

    @Override
    public <V extends UseCase.ResponseValues> void onError(final V response, final UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(() -> useCaseCallback.onError(response));
    }

}
