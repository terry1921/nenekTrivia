package com.fenixarts.nenektrivia.game.domain.usecases;

import androidx.annotation.NonNull;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.data.source.QuestionDataSource;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;
import com.fenixarts.nenektrivia.game.domain.models.Questions;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 25/01/18 - 17:48.
 */

public class GetAllQuestions extends UseCase<GetAllQuestions.RequestValues, GetAllQuestions.ResponseValues>{

    @NonNull
    private final QuestionRepository mRepository;

    public GetAllQuestions(@NonNull final QuestionRepository repository) {
        mRepository = checkNotNull(repository);
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        if (requestValues.isForceUpdate()){
            mRepository.refreshTasks();
        }

        mRepository.getQuestions(new QuestionDataSource.LoadQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Questions> questions) {
                getUseCaseCallback().onSuccess(new ResponseValues(true));
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues{
        private final boolean forceUpdate;

        public RequestValues(boolean forceUpdate) {
            this.forceUpdate = checkNotNull(forceUpdate);
        }

        boolean isForceUpdate() {
            return forceUpdate;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues{

        private boolean success;
        private Questions question;
        private String error;

        public ResponseValues(Questions question) {
            this.question = checkNotNull(question);
        }

        public ResponseValues(boolean success) {
            this.success = checkNotNull(success);
        }

        public boolean isSuccess() {
            return success;
        }

        public Questions getQuestion() {
            return question;
        }

        public void setError(String error) {
            this.error = checkNotNull(error);
        }

        public String getError() {
            return error;
        }
    }
}
