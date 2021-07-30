package com.fenixarts.nenektrivia.data.source;

import androidx.annotation.NonNull;

import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.bestplayers.domain.usecases.GetBestPlayerList;
import com.fenixarts.nenektrivia.data.source.local.LocalDataSource;
import com.fenixarts.nenektrivia.data.source.remote.RemoteDataSource;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 10/01/18 - 11:24.
 */

public class Repository implements DataSource {

    private static Repository INSTANCE = null;
    private final DataSource mRemoteDataSource;
    private final DataSource mLocalDataSource;

    private boolean mCacheIsDirty;
    /* best players data */
    private LinkedHashMultimap<String, BestPlayersItem> mCachedBestPlayers;

    // Constructor privado para prevenir la creacion de instancia directa
    private Repository(DataSource remoteDataSource, DataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    /**
     * Regresa una sola instancia de esta clase, la crea si es necesario
     * @param remoteDataSource Fuente de datos del servidor remoto
     * @param localDataSource Fuente de datos de la base de datos del dispositivo
     * @return la instancia de {@link Repository}
     */
    public static Repository getInstance(DataSource remoteDataSource, DataSource localDataSource){
        if (INSTANCE == null){
            INSTANCE = new Repository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    /**
     * Usado para forzar a {@link #getInstance(DataSource, DataSource)} a crear unanueva INSTANCE
     * la siguiente vez que sea llamado.
     */
    public static void destroyInstance(){
        RemoteDataSource.destroyInstance();
        LocalDataSource.destroyInstance();
        INSTANCE = null;
    }

    @Override
    public void getBestPlayerList(@NonNull final GetBestPlayerList.RequestValues requestValues, @NonNull final LoadBestPlayerCallback callback) {
        checkNotNull(requestValues);
        checkNotNull(callback);

        if (mCachedBestPlayers != null && !mCacheIsDirty){
            callback.onDataLoaded(Lists.newArrayList(mCachedBestPlayers.values()));
            return;
        }

        if (mCacheIsDirty){
            getBestPlayersFromRemoteDataSource(requestValues, callback);
        }else{
            mLocalDataSource.getBestPlayerList(requestValues, new LoadBestPlayerCallback() {
                @Override
                public void onDataLoaded(@NonNull Collection<BestPlayersItem> collection) {
                    refreshBestPlayerCached(collection);
                    callback.onDataLoaded(Lists.newArrayList(mCachedBestPlayers.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getBestPlayersFromRemoteDataSource(requestValues, callback);
                }

                @Override
                public void onDataNotAvailable(@NonNull String error) {
                    getBestPlayersFromRemoteDataSource(requestValues, callback);
                }
            });
        }

    }

    @Override
    public void getPromotions(@NonNull final LoadPromotionsCallback callback) {
        checkNotNull(callback);

        mRemoteDataSource.getPromotions(callback);
    }

    private void getBestPlayersFromRemoteDataSource(GetBestPlayerList.RequestValues requestValues, final LoadBestPlayerCallback callback) {
        mRemoteDataSource.getBestPlayerList(requestValues, new LoadBestPlayerCallback() {
            @Override
            public void onDataLoaded(@NonNull Collection<BestPlayersItem> collection) {
                refreshBestPlayerCached(collection);
                refreshBestPlayerLocalDataSource(collection);
                callback.onDataLoaded(Lists.newArrayList(mCachedBestPlayers.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }

            @Override
            public void onDataNotAvailable(@NonNull String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    private void refreshBestPlayerCached(final Collection<BestPlayersItem> collection) {
        if (mCachedBestPlayers == null){
            mCachedBestPlayers = LinkedHashMultimap.create();
        }
        mCachedBestPlayers.clear();
        for (BestPlayersItem item : collection){
            mCachedBestPlayers.put(item.getId(),item);
        }
        mCacheIsDirty = false;
    }

    private void refreshBestPlayerLocalDataSource(final Collection<BestPlayersItem> collection) {
        mLocalDataSource.deleteAllBestPlayers();
        for (BestPlayersItem item : collection){
            mLocalDataSource.saveBestPlayer(item);
        }
    }

    @Override
    public void refreshBestPlayerList() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllBestPlayers() {
        mLocalDataSource.deleteAllBestPlayers();
        mRemoteDataSource.deleteAllBestPlayers();

        if (mCachedBestPlayers == null) {
            mCachedBestPlayers = LinkedHashMultimap.create();
        }
        mCachedBestPlayers.clear();
    }

    @Override
    public void saveBestPlayer(BestPlayersItem item) {
        checkNotNull(item);

        mLocalDataSource.saveBestPlayer(item);
        mRemoteDataSource.saveBestPlayer(item);

        if (mCachedBestPlayers == null) {
            mCachedBestPlayers = LinkedHashMultimap.create();
        }

        mCachedBestPlayers.put(item.getId(),item);

    }
}
