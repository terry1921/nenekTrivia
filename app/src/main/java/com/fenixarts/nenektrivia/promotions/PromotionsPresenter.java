package com.fenixarts.nenektrivia.promotions;

import android.support.annotation.NonNull;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.UseCaseHandler;
import com.fenixarts.nenektrivia.promotions.domain.usecases.GetPromotionsUseCase;

/**
 * NenekTrivia
 * Created by terry0022 on 19/02/18 - 18:30.
 */

public class PromotionsPresenter implements Contract.Presenter {

    @NonNull
    private final Contract.View mView;
    @NonNull
    private final UseCaseHandler mUseCaseHandler;
    @NonNull
    private final GetPromotionsUseCase mUseCase;

    PromotionsPresenter(@NonNull final Contract.View view,
                        @NonNull final UseCaseHandler useCaseHandler,
                        @NonNull final GetPromotionsUseCase useCase) {
        mView = view;
        mUseCaseHandler = useCaseHandler;
        mUseCase = useCase;
    }

    @Override
    public void onStart() {
        mView.setLoadingIndicator(true);
        mUseCaseHandler.execute(mUseCase, new GetPromotionsUseCase.RequestValues(), new UseCase.UseCaseCallback<GetPromotionsUseCase.ResponseValues>() {
            @Override
            public void onSuccess(GetPromotionsUseCase.ResponseValues response) {
                mView.setLoadingIndicator(false);
                mView.showPromotions(response.getCollection());
            }

            @Override
            public void onError() {
                mView.setLoadingIndicator(false);
                mView.onError(R.string.error_load_data);
            }

            @Override
            public void onError(GetPromotionsUseCase.ResponseValues response) {
                mView.setLoadingIndicator(false);
                mView.onError(response.getError());
            }
        });
    }
}
