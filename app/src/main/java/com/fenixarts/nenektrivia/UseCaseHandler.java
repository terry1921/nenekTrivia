package com.fenixarts.nenektrivia;

/**
 * QuinielaPicante
 * Created by terry0022 on 21/09/17 - 16:46.
 */

public class UseCaseHandler {

    /* constante de instancia */
    private static UseCaseHandler instance;

    /* planificador */
    private final UseCaseScheduler mUseCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    public <T extends UseCase.RequestValues, S extends UseCase.ResponseValues> void execute(
            final UseCase<T, S> useCase, T values, UseCase.UseCaseCallback<S> useCaseCallback) {

        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper<>(useCaseCallback, this));

        mUseCaseScheduler.execute(useCase::run);
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValues> implements UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        UiCallbackWrapper(UseCase.UseCaseCallback<V> callback, UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }

        @Override
        public void onError(V response) {
            mUseCaseHandler.notifyError(response, mCallback);
        }
    }

    private <V extends UseCase.ResponseValues> void notifyError(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.onError(response, useCaseCallback);
    }

    private <V extends UseCase.ResponseValues> void notifyError(UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.onError(useCaseCallback);
    }

    private <V extends UseCase.ResponseValues> void notifyResponse(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    public static UseCaseHandler getInstance() {
        if (instance == null) {
            instance = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return instance;
    }
}
