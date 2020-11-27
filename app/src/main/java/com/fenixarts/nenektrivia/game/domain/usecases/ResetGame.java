package com.fenixarts.nenektrivia.game.domain.usecases;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 30/01/18 - 00:54.
 */

public class ResetGame extends UseCase<ResetGame.RequestValues, ResetGame.ResponseValues> {

    private final QuestionRepository mRepository;

    public ResetGame(QuestionRepository repository) {
        mRepository = checkNotNull(repository);
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        QuestionRepository.destroyInstance();
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static class RequestValues implements UseCase.RequestValues{

    }

    public static class ResponseValues implements UseCase.ResponseValues{

    }

}
