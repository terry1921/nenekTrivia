package com.fenixarts.nenektrivia.game;

import androidx.annotation.NonNull;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.UseCaseHandler;
import com.fenixarts.nenektrivia.game.domain.models.Answers;
import com.fenixarts.nenektrivia.game.domain.models.Questions;
import com.fenixarts.nenektrivia.game.domain.usecases.GetAllQuestions;
import com.fenixarts.nenektrivia.game.domain.usecases.GetRandomQuestion;
import com.fenixarts.nenektrivia.game.domain.usecases.ResetGame;
import com.fenixarts.nenektrivia.game.domain.usecases.SumatePoint;
import com.fenixarts.nenektrivia.utils.Constants;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 17/01/18 - 12:22.
 */

public class GamePresenter implements GameContract.Presenter {

    @NonNull
    private final GameContract.View mView;
    @NonNull
    private final UseCaseHandler mUseCaseHandler;
    private final GetAllQuestions mGetAllQuestions;
    @NonNull
    private final GetRandomQuestion mGetRandomQuestion;
    @NonNull
    private final ResetGame mResetGame;
    private final SumatePoint mSumatePoint;
    private Boolean forceUpdate = false;
    private static int gamming = Constants.STATUS_NO_GAME;

    GamePresenter(@NonNull final GameContract.View view,
                  @NonNull final UseCaseHandler useCaseHandler,
                  @NonNull final GetAllQuestions getAllQuestions,
                  @NonNull final GetRandomQuestion getRandomQuestion,
                  @NonNull final SumatePoint sumatePoint,
                  @NonNull final ResetGame resetGame) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mView = checkNotNull(view, "View cannot be null!");
        mGetAllQuestions = checkNotNull(getAllQuestions, "getAllQuestions cannot be null!");
        mGetRandomQuestion = checkNotNull(getRandomQuestion, "getRandomQuestion cannot be null!");
        mSumatePoint = checkNotNull(sumatePoint);
        mResetGame = checkNotNull(resetGame, "resetGame cannot be null!");

        mView.setPresenter(this);
    }

    @Override
    public void onStart() {
        loadQuestions(forceUpdate);
    }


    private void loadQuestions(boolean forceUpdate) {
        GetAllQuestions.RequestValues request = new GetAllQuestions.RequestValues(forceUpdate);
        mUseCaseHandler.execute(mGetAllQuestions, request, new UseCase.UseCaseCallback<GetAllQuestions.ResponseValues>() {
            @Override
            public void onSuccess(GetAllQuestions.ResponseValues response) {
                mView.questionsLoaded();
            }

            @Override
            public void onError() {
                mView.onError();
            }

            @Override
            public void onError(GetAllQuestions.ResponseValues response) {
                mView.onError(response.getError());
            }
        });
    }

    @Override
    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    @Override
    public void startGame() {
        gamming = Constants.STATUS_START_GAME;
    }

    @Override
    public void isGamming() {
        gamming = Constants.STATUS_GAMMING;
    }

    @Override
    public void endGame() {
        gamming = Constants.STATUS_END_GAME;
    }

    @Override
    public void getRandomQuestion() {
        mUseCaseHandler.execute(mGetRandomQuestion, new GetRandomQuestion.RequestValues(), new UseCase.UseCaseCallback<GetRandomQuestion.ResponseValues>() {
            @Override
            public void onSuccess(GetRandomQuestion.ResponseValues response) {
                List<Answers> answers = processResponse(response.getQuestion());
                Collections.shuffle(answers);
                mView.displayQuestion(response.getQuestion().getQuestion(),answers);
            }

            @Override
            public void onError() {
                mView.onError();
            }

            @Override
            public void onError(GetRandomQuestion.ResponseValues response) {
                String error = response.getError();
                if (Constants.END_GAME.equals(error)){
                    mView.endQuestionsGame();
                }else{
                    mView.onError(response.getError());
                }
            }
        });
    }

    @Override
    public void sumatePoint() {
        final SumatePoint.RequestValues request = new SumatePoint.RequestValues(gamming);
        mUseCaseHandler.execute(mSumatePoint, request, new UseCase.UseCaseCallback<SumatePoint.ResponseValues>() {
            @Override
            public void onSuccess(SumatePoint.ResponseValues response) {
                mView.startedGame();
            }

            @Override
            public void onError() {
                mView.onError();
            }

            @Override
            public void onError(SumatePoint.ResponseValues response) {
                mView.onError(response.getError());
            }
        });
    }

    @Override
    public void sumatePoint(final boolean correct) {
        final SumatePoint.RequestValues request = new SumatePoint.RequestValues(gamming);
        mUseCaseHandler.execute(mSumatePoint, request, new UseCase.UseCaseCallback<SumatePoint.ResponseValues>() {
            @Override
            public void onSuccess(SumatePoint.ResponseValues response) {
                if (response.getEnded()){
                    mView.endGame(response.getPoints());
                } else{
                    mView.pointSumate();
                }
            }

            @Override
            public void onError() {
                mView.onError();
            }

            @Override
            public void onError(SumatePoint.ResponseValues response) {
                mView.onError(response.getError());
            }
        });
    }

    @Override
    public void onDestroy() {
        mUseCaseHandler.execute(mResetGame, new ResetGame.RequestValues(), new UseCase.UseCaseCallback<ResetGame.ResponseValues>() {
            @Override
            public void onSuccess(ResetGame.ResponseValues response) {
                mView.resetSuccess();
            }

            @Override
            public void onError() {

            }

            @Override
            public void onError(ResetGame.ResponseValues response) {

            }
        });
    }

    private List<Answers> processResponse(Questions question) {
        List<Answers> list = Lists.newArrayList();
        list.add(new Answers(question.getId(),question.getAnswerbad01(),false));
        list.add(new Answers(question.getId(),question.getAnswerbad02(),false));
        list.add(new Answers(question.getId(),question.getAnswerbad03(),false));
        list.add(new Answers(question.getId(),question.getAnswergood(),true));
        return list;
    }
}
