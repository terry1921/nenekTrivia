package com.fenixarts.nenektrivia.data.source;

import com.fenixarts.nenektrivia.game.domain.models.Questions;

import java.util.List;

/**
 * NenekTrivia
 * Created by terry0022 on 26/01/18 - 11:33.
 */

public interface QuestionDataSource {

    interface LoadQuestionsCallback {

        void onQuestionsLoaded(List<Questions> questions);

        void onDataNotAvailable();

    }

    interface LoadRandomQuestionCallback{

        void onQuestionLoaded(Questions question);

        void onDataNotAvailable();

        void endQuestionsGame();

    }

    interface SumatePointCallback{

        void onSumatePoint();

        void onError();

        void onGameEnded(int totalPoint);

    }

    void refreshTasks();

    void getQuestions(final LoadQuestionsCallback callback);

    void deleteAllQuestions();

    void saveQuestion(Questions question);

    void getRandomQuestion(final LoadRandomQuestionCallback callback);

    void sumatePoint(int gamming, SumatePointCallback callback);

}