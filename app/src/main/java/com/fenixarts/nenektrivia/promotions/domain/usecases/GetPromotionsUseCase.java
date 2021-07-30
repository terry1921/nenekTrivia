package com.fenixarts.nenektrivia.promotions.domain.usecases;

import androidx.annotation.NonNull;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.data.source.DataSource;
import com.fenixarts.nenektrivia.data.source.Repository;
import com.fenixarts.nenektrivia.promotions.domain.models.PromotionRow;

import java.util.Collection;

/**
 * NenekTrivia
 * Created by terry0022 on 19/02/18 - 23:09.
 */

public class GetPromotionsUseCase extends UseCase<GetPromotionsUseCase.RequestValues, GetPromotionsUseCase.ResponseValues>{

    private final Repository repository;

    public GetPromotionsUseCase(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        repository.getPromotions(new DataSource.LoadPromotionsCallback() {
            @Override
            public void onDataLoaded(@NonNull Collection<PromotionRow> collection) {
                ResponseValues response = new ResponseValues(collection);
                getUseCaseCallback().onSuccess(response);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }

            @Override
            public void onDataNotAvailable(@NonNull String error) {
                ResponseValues response = new ResponseValues(null);
                response.setError(error);
                getUseCaseCallback().onError(response);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues{}

    public static class ResponseValues implements UseCase.ResponseValues{

        private String error;
        private Collection<PromotionRow> collection;

        public ResponseValues(Collection<PromotionRow> collection) {
            this.collection = collection;
        }

        public String getError() {
            return error;
        }

        public Collection<PromotionRow> getCollection() {
            return collection;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

}
