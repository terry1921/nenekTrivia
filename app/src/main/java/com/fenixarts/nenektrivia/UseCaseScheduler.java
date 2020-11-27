package com.fenixarts.nenektrivia;

/**
 * QuinielaPicante
 * Created by terry0022 on 21/09/17 - 16:45.
 */

public interface UseCaseScheduler {

    /**
     * ejecucion
     * @param runnable prepara ejecucion
     */
    void execute(Runnable runnable);

    /**
     * Notificacion de respuesta
     * @param response respuesta
     * @param useCaseCallback callback de caso de uso
     * @param <V> datos
     */
    <V extends UseCase.ResponseValues> void notifyResponse(final V response,
                                                           final UseCase.UseCaseCallback<V> useCaseCallback);

    /**
     * En Caso de error
     * @param useCaseCallback callback del caso de uso
     * @param <V> datos
     */
    <V extends UseCase.ResponseValues> void onError(
            final UseCase.UseCaseCallback<V> useCaseCallback);


    <V extends UseCase.ResponseValues> void onError(V response,
                                                    UseCase.UseCaseCallback<V> useCaseCallback);

}
