package com.fenixarts.nenektrivia.bestplayers.domain.usecases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.data.source.DataSource;
import com.fenixarts.nenektrivia.data.source.Repository;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 23/01/18 - 16:07.
 */

public class GetBestPlayerList extends UseCase<GetBestPlayerList.RequestValues, GetBestPlayerList.ResponseValues>{

    @NonNull
    private final Repository mRepository;

    public GetBestPlayerList(@NonNull final Repository repository) {
        mRepository = checkNotNull(repository, "Repository can't be null");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        checkNotNull(requestValues);
        if (requestValues.isForceUpdate()) {
            mRepository.refreshBestPlayerList();
        }

        mRepository.getBestPlayerList(requestValues, new DataSource.LoadBestPlayerCallback() {
            @Override
            public void onDataLoaded(@NonNull Collection<BestPlayersItem> collection) {
                ResponseValues response = new ResponseValues(collection);
                getUseCaseCallback().onSuccess(response);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }

            @Override
            public void onDataNotAvailable(@NonNull String error) {
                ResponseValues responseValues = new ResponseValues(null);
                responseValues.setError(error);
                getUseCaseCallback().onError(responseValues);
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues{

        private boolean forceUpdate;

        public RequestValues(boolean forceUpdate) {
            this.forceUpdate = checkNotNull(forceUpdate);
        }

        boolean isForceUpdate() {
            return forceUpdate;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues{

        private final Collection<BestPlayersItem> collection;
        private String error;

        ResponseValues(@Nullable final Collection<BestPlayersItem> collection) {
            this.collection = checkNotNull(collection);
        }

        public Collection<BestPlayersItem> getCollection() {
            return collection;
        }

        void setError(String error) {
            this.error = checkNotNull(error);
        }

        public String getError() {
            return error;
        }
    }

}
