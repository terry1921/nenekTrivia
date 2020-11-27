package com.fenixarts.nenektrivia;

/**
 * NenekTrivia
 * Created by terry0022 on 07/03/18 - 11:19.
 */

public class TestUseCaseScheduler implements UseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <V extends UseCase.ResponseValues> void notifyResponse(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        useCaseCallback.onSuccess(response);
    }

    @Override
    public <V extends UseCase.ResponseValues> void onError(UseCase.UseCaseCallback<V> useCaseCallback) {
        useCaseCallback.onError();
    }

    @Override
    public <V extends UseCase.ResponseValues> void onError(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        useCaseCallback.onError(response);
    }
}
