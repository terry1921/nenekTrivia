package com.fenixarts.nenektrivia.bestplayers;

import android.support.annotation.NonNull;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.UseCaseHandler;
import com.fenixarts.nenektrivia.bestplayers.domain.usecases.GetBestPlayerList;

/**
 * NenekTrivia
 * Created by terry0022 on 12/01/18 - 11:58.
 */

public class BestPlayersPresenter implements BestPlayersContract.Presenter {

    private final BestPlayersContract.View mView;
    @NonNull
    private final UseCaseHandler mUseCaseHandler;
    @NonNull
    private final GetBestPlayerList mUseCase;

    BestPlayersPresenter(@NonNull final BestPlayersContract.View view,
                         @NonNull final UseCaseHandler useCaseHandler,
                         @NonNull final GetBestPlayerList bestPlayerListUseCase) {
        mView = view;
        mUseCaseHandler = useCaseHandler;
        mUseCase = bestPlayerListUseCase;
    }

    @Override
    public void onStart() {
        mView.setLoadingIndicator(true);
        GetBestPlayerList.RequestValues request = new GetBestPlayerList.RequestValues(true);
        mUseCaseHandler.execute(mUseCase, request, new UseCase.UseCaseCallback<GetBestPlayerList.ResponseValues>() {
            @Override
            public void onSuccess(GetBestPlayerList.ResponseValues response) {
                mView.setLoadingIndicator(false);
                mView.showBestPlayers(response.getCollection());
            }

            @Override
            public void onError() {
                mView.setLoadingIndicator(false);
                mView.onError(R.string.error_load_data);
            }

            @Override
            public void onError(GetBestPlayerList.ResponseValues response) {
                mView.setLoadingIndicator(false);
                mView.onError(response.getError());
            }
        });
    }

}
