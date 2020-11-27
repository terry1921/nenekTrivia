package com.fenixarts.nenektrivia.game.domain.usecases;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.data.source.QuestionDataSource;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 30/01/18 - 12:28.
 */

public class SumatePoint extends UseCase<SumatePoint.RequestValues, SumatePoint.ResponseValues> {

    private final QuestionRepository mRepository;

    public SumatePoint(QuestionRepository repository) {
        mRepository = checkNotNull(repository);
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mRepository.sumatePoint(requestValues.isGamming(), new QuestionDataSource.SumatePointCallback() {
            @Override
            public void onSumatePoint() {
                getUseCaseCallback().onSuccess(new ResponseValues());
            }

            @Override
            public void onError() {
                getUseCaseCallback().onError();
            }

            @Override
            public void onGameEnded(int totalPoint) {
                ResponseValues response = new ResponseValues();
                response.setEnded(true);
                response.setPoints(checkNotNull(totalPoint));
                getUseCaseCallback().onSuccess(response);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues{

        private final int gamming;

        public RequestValues(int gamming) {
            this.gamming = gamming;
        }

        int isGamming() {
            return gamming;
        }
    }


    public static class ResponseValues implements UseCase.ResponseValues{

        private String error;
        private boolean ended = false;
        private int points;

        public void setError(String error) {
            this.error = checkNotNull(error);
        }

        public String getError() {
            return error;
        }

        void setEnded(@SuppressWarnings("SameParameterValue") boolean ended) {
            this.ended = checkNotNull(ended);
        }

        public boolean getEnded() {
            return ended;
        }

        public void setPoints(int points) {
            this.points = checkNotNull(points);
        }

        public int getPoints() {
            return points;
        }
    }

}
