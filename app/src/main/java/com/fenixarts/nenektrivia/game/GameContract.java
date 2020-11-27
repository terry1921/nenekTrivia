package com.fenixarts.nenektrivia.game;

import com.fenixarts.nenektrivia.base.BasePresenter;
import com.fenixarts.nenektrivia.base.BaseView;
import com.fenixarts.nenektrivia.game.domain.models.Answers;

import java.util.List;

/**
 * NenekTrivia
 * Created by terry0022 on 16/01/18 - 18:01.
 */

public interface GameContract {

    interface View extends BaseView<Presenter>{

        void displayQuestion(final String question, final List<Answers> answers);

        void questionsLoaded();

        void onError();

        void onError(final String error);

        void startedGame();

        void pointSumate();

        void resetSuccess();

        void endQuestionsGame();

        void endGame(int points);
    }

    interface Presenter extends BasePresenter{

        void setForceUpdate(Boolean forceUpdate);

        void startGame();

        void isGamming();

        void endGame();

        void getRandomQuestion();

        void sumatePoint();

        void sumatePoint(boolean correct);

        void onDestroy();
    }

}
