package com.fenixarts.nenektrivia.profile;

import androidx.annotation.NonNull;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.UseCaseHandler;
import com.fenixarts.nenektrivia.profile.domain.usecase.GetUserData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 15/01/18 - 13:48.
 */

public class ProfilePresenter implements ProfileContract.Presenter {


    @NonNull
    private final ProfileContract.View mView;
    @NonNull
    private final UseCaseHandler mUseCaseHandler;
    @NonNull
    private final GetUserData mUserData;

    ProfilePresenter(@NonNull final ProfileContract.View view,
                            @NonNull final UseCaseHandler useCaseHandler,
                            @NonNull final GetUserData userData) {
        this.mView = checkNotNull(view);
        this.mUseCaseHandler = checkNotNull(useCaseHandler);
        this.mUserData = checkNotNull(userData);
    }

    @Override
    public void onStart() {
        mUseCaseHandler.execute(mUserData, new GetUserData.RequestValues(), new UseCase.UseCaseCallback<GetUserData.ResponseValues>() {
            @Override
            public void onSuccess(GetUserData.ResponseValues response) {
                mView.setUserData(response.getProfile());
            }

            @Override
            public void onError() {
                mView.onError();
            }

            @Override
            public void onError(GetUserData.ResponseValues response) {
                mView.onError(response.getError());
            }
        });
    }
}
