package com.fenixarts.nenektrivia.data.source;

import android.support.annotation.NonNull;

import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.bestplayers.domain.usecases.GetBestPlayerList;
import com.fenixarts.nenektrivia.promotions.domain.models.PromotionRow;

import java.util.Collection;

/**
 * NenekTrivia
 * Created by terry0022 on 10/01/18 - 11:24.
 */

public interface DataSource {

    void getBestPlayerList(@NonNull final GetBestPlayerList.RequestValues requestValues,
                           @NonNull final LoadBestPlayerCallback callback);

    void getPromotions(@NonNull final LoadPromotionsCallback callback);

    interface LoadBestPlayerCallback{

        void onDataLoaded(@NonNull final Collection<BestPlayersItem> collection);

        void onDataNotAvailable();

        void onDataNotAvailable(@NonNull final String error);

    }

    interface LoadPromotionsCallback{

        void onDataLoaded(@NonNull final Collection<PromotionRow> collection);

        void onDataNotAvailable();

        void onDataNotAvailable(@NonNull final String error);

    }

    void refreshBestPlayerList();

    void deleteAllBestPlayers();

    void saveBestPlayer(final BestPlayersItem item);

}
