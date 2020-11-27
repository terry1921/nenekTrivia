package com.fenixarts.nenektrivia.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.bestplayers.domain.usecases.GetBestPlayerList;
import com.fenixarts.nenektrivia.data.source.DataSource;
import com.fenixarts.nenektrivia.promotions.domain.models.PromotionRow;
import com.fenixarts.nenektrivia.utils.Constants;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 15/01/18 - 13:31.
 */

public class RemoteDataSource implements DataSource {

    private static final String TAG = "RemoteDataSource";
    private static RemoteDataSource INSTANCE = null;
    private final DatabaseReference mDatabase;
    private List<BestPlayersItem> bestPlayersList = Lists.newArrayList();
    private List<PromotionRow> promotionRowList = Lists.newArrayList();

    // Constructor privado para prevenir la creacion de instancia directa
    private RemoteDataSource(@NonNull final Context context) {
        checkNotNull(context);
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Regresa una sola instancia de esta clase, la crea si es necesario
     *
     * @param context contexto
     * @return la instancia de {@link RemoteDataSource}
     */
    public static RemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(context);
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
    public void getBestPlayerList(@NonNull GetBestPlayerList.RequestValues requestValues, @NonNull final LoadBestPlayerCallback callback) {
        Query mBestPlayerQuery = mDatabase.child(Constants.HONOR).orderByChild(Constants.POINTS).limitToLast(50);
        mBestPlayerQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bestPlayersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BestPlayersItem item = snapshot.getValue(BestPlayersItem.class);
                    bestPlayersList.add(item);
                }
                callback.onDataLoaded(Lists.reverse(bestPlayersList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Print.e(TAG, databaseError.getMessage(), databaseError.toException());
                callback.onDataNotAvailable(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getPromotions(@NonNull final LoadPromotionsCallback callback) {
        Query mBestPlayerQuery = mDatabase.child(Constants.PROMOTIONS);
        mBestPlayerQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                promotionRowList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    PromotionRow item = snapshot.getValue(PromotionRow.class);
                    promotionRowList.add(item);
                }
                Collections.shuffle(promotionRowList);
                callback.onDataLoaded(promotionRowList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Print.e(TAG, databaseError.getMessage(), databaseError.toException());
                callback.onDataNotAvailable(databaseError.getMessage());
            }
        });
    }

    @Override
    public void refreshBestPlayerList() {
        // Logic in Repository
    }

    @Override
    public void deleteAllBestPlayers() {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(String.format("/%s/", Constants.HONOR), null);
        mDatabase.updateChildren(childUpdates);
    }

    @Override
    public void saveBestPlayer(BestPlayersItem item) {
        mDatabase.child(Constants.HONOR).child(item.getId()).setValue(item);
    }

}
