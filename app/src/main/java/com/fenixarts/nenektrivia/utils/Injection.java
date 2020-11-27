package com.fenixarts.nenektrivia.utils;

import android.content.Context;

import com.fenixarts.nenektrivia.UseCaseHandler;
import com.fenixarts.nenektrivia.bestplayers.domain.usecases.GetBestPlayerList;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;
import com.fenixarts.nenektrivia.data.source.Repository;
import com.fenixarts.nenektrivia.data.source.local.LocalDataSource;
import com.fenixarts.nenektrivia.data.source.local.QuestionLocalDataSource;
import com.fenixarts.nenektrivia.data.source.remote.QuestionRemoteDataSource;
import com.fenixarts.nenektrivia.data.source.remote.RemoteDataSource;
import com.fenixarts.nenektrivia.game.domain.usecases.GetAllQuestions;
import com.fenixarts.nenektrivia.game.domain.usecases.GetRandomQuestion;
import com.fenixarts.nenektrivia.game.domain.usecases.ResetGame;
import com.fenixarts.nenektrivia.game.domain.usecases.SumatePoint;
import com.fenixarts.nenektrivia.profile.domain.usecase.GetUserData;
import com.fenixarts.nenektrivia.promotions.domain.usecases.GetPromotionsUseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 03/01/18 - 10:11.
 */

public class Injection {

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    private static Repository provideRepository(Context context) {
        checkNotNull(context, "Context can't be null");
        return Repository.getInstance(
                RemoteDataSource.getInstance(context),
                LocalDataSource.getInstance(context));
    }

    private static QuestionRepository provideQuestionRepository(Context context) {
        checkNotNull(context, "Context can't be null");
        return QuestionRepository.getInstance(
                QuestionRemoteDataSource.getInstance(context),
                QuestionLocalDataSource.getInstance(context));
    }

    public static GetBestPlayerList provideGetBestPlayerUseCase(Context context) {
        return new GetBestPlayerList(Injection.provideRepository(context));
    }

    public static GetAllQuestions provideGetQuestionsUseCase(Context context) {
        return new GetAllQuestions(Injection.provideQuestionRepository(context));
    }

    public static GetRandomQuestion provideGetRandomQuestionUseCase(Context context) {
        return new GetRandomQuestion(Injection.provideQuestionRepository(context));
    }

    public static SumatePoint provideSumatePointUseCase(Context context) {
        return new SumatePoint(Injection.provideQuestionRepository(context));
    }

    public static ResetGame provideResetGameUseCase(Context context) {
        return new ResetGame(Injection.provideQuestionRepository(context));
    }

    public static GetUserData provideGetUserData(Context context) {
        return new GetUserData(Injection.provideRepository(context));
    }

    public static GetPromotionsUseCase provideGetPromotionsUseCase(Context context) {
        return new GetPromotionsUseCase(Injection.provideRepository(context));
    }
}
