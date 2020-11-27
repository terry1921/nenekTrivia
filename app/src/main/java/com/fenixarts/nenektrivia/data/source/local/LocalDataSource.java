package com.fenixarts.nenektrivia.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.bestplayers.domain.usecases.GetBestPlayerList;
import com.fenixarts.nenektrivia.data.source.DataSource;
import com.fenixarts.nenektrivia.data.source.remote.RemoteDataSource;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 15/01/18 - 13:31.
 */

public class LocalDataSource implements DataSource {

    private static final String TAG = "LocalDataSource";
    private static LocalDataSource INSTANCE = null;
    private final Gson gson;
    private SQLite sqLite;

    // Constructor privado para prevenir la creacion de instancia directa
    private LocalDataSource(@NonNull final Context context) {
        checkNotNull(context);
        sqLite = new SQLite(context);
        gson = new Gson();
    }

    /**
     * Regresa una sola instancia de esta clase, la crea si es necesario
     *
     * @param context contexto
     * @return la instancia de {@link RemoteDataSource}
     */
    public static LocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Usado para forzar a {@link #getInstance(Context)} a crear una nueva INSTANCE
     * la siguiente vez que sea llamado.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getBestPlayerList(@NonNull GetBestPlayerList.RequestValues requestValues, @NonNull LoadBestPlayerCallback callback) {
        JSONArray dataList = null;
        try {
            sqLite.open();
            dataList = sqLite.printList(sqLite.selectAllBestPlayers());
            sqLite.close();
        } catch (JSONException e) {
            Print.e(TAG, e.getMessage(), e.getCause());
        }

        if (dataList != null && dataList.length() > 0){
            BestPlayersItem[] items = gson.fromJson(dataList.toString(), BestPlayersItem[].class);
            callback.onDataLoaded(Lists.newArrayList(items));
        }else{
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void getPromotions(@NonNull LoadPromotionsCallback callback) {
        // logic in remote data source
    }

    @Override
    public void refreshBestPlayerList() {
        // logic in repository
    }

    @Override
    public void deleteAllBestPlayers() {
        int delete = sqLite.deleteBestPlayers();
        Print.d(TAG, String.format(Locale.US,"deletes items = [%d]", delete));
    }

    @Override
    public void saveBestPlayer(BestPlayersItem item) {
        checkNotNull(item);

        long insert = sqLite.insertBestPlayers(item);

        Print.d(TAG, String.format(Locale.US,"insert items = [%d]", insert));
    }
}
