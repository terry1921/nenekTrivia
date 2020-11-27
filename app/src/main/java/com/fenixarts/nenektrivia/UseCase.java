package com.fenixarts.nenektrivia;

/**
 * QuinielaPicante
 * Created by terry0022 on 21/09/17 - 16:45.
 */

public abstract class UseCase <Q extends UseCase.RequestValues, P extends UseCase.ResponseValues> {

    private Q mRequestValues;
    private UseCaseCallback<P> mUseCaseCallback;

    /**
     * getter
     * @return Datos de peticion
     */
    public Q getRequestValues() {
        return mRequestValues;
    }

    /**
     * setter
     * @param requestValues Datos de peticion
     */
    public void setRequestValues(Q requestValues) {
        this.mRequestValues = requestValues;
    }

    /**
     * getter
     * @return callback de respuesta
     */
    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    /**
     * setter
     * @param useCaseCallback callback de respuesta
     */
    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        this.mUseCaseCallback = useCaseCallback;
    }

    public void run() {
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(Q requestValues);

    /**
     * Datos transmitidos a una peticion
     */
    public interface RequestValues {
    }

    /**
     * Datos recividos de una peticion
     */
    public interface ResponseValues {
    }

    /**
     * callback para usar en respuesta de la peticion
     * @param <R>
     */
    public interface UseCaseCallback<R> {

        void onSuccess(R response);

        void onError();

        void onError(R response);

    }

}
