package com.fenixarts.nenektrivia.game.domain.usecases;

import androidx.annotation.Nullable;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.data.source.QuestionDataSource;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;
import com.fenixarts.nenektrivia.game.domain.models.Questions;
import com.fenixarts.nenektrivia.utils.Constants;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 29/01/18 - 22:27.
 */

public class GetRandomQuestion extends UseCase<GetRandomQuestion.RequestValues, GetRandomQuestion.ResponseValues> {

    private final QuestionRepository mRepository;

    public GetRandomQuestion(QuestionRepository repository) {
        this.mRepository = checkNotNull(repository);
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mRepository.getRandomQuestion(new QuestionDataSource.LoadRandomQuestionCallback() {
            @Override
            public void onQuestionLoaded(Questions question) {
                ResponseValues response = new ResponseValues(question);
                getUseCaseCallback().onSuccess(response);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }

            @Override
            public void endQuestionsGame() {
                ResponseValues response = new ResponseValues(null);
                response.setError(Constants.END_GAME);
                getUseCaseCallback().onError(response);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues{}

    public static class ResponseValues implements UseCase.ResponseValues{

        private String error;
        private Questions question;

        public ResponseValues(@Nullable Questions question) {
            this.question = question;
        }

        public void setError(String error) {
            this.error = checkNotNull(error);
        }

        public String getError() {
            return error;
        }

        public Questions getQuestion() {
            return question;
        }
    }

}
