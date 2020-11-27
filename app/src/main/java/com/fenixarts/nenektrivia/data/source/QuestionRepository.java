package com.fenixarts.nenektrivia.data.source;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.fenixarts.nenektrivia.data.source.local.QuestionLocalDataSource;
import com.fenixarts.nenektrivia.data.source.remote.QuestionRemoteDataSource;
import com.fenixarts.nenektrivia.game.domain.models.Questions;
import com.fenixarts.nenektrivia.utils.Constants;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 26/01/18 - 11:33.
 */

public class QuestionRepository implements QuestionDataSource {

    private static final String TAG = "QuestionRepository";
    private static QuestionRepository INSTANCE = null;
    private final QuestionDataSource mRemoteDataSource;
    private final QuestionDataSource mLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    private LinkedHashMultimap<String, Questions> mCachedQuestions;
    private HashMultimap<String, Questions> mQuestionsGame;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;
    private boolean canContinue = true;

    // Constructor privado para prevenir la creacion de instancia directa
    private QuestionRepository(QuestionDataSource remoteDataSource, QuestionDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    /**
     * Regresa una sola instancia de esta clase, la crea si es necesario
     * @param remoteDataSource Fuente de datos del servidor remoto
     * @param localDataSource Fuente de datos de la base de datos del dispositivo
     * @return la instancia de {@link Repository}
     */
    public static QuestionRepository getInstance(QuestionDataSource remoteDataSource, QuestionDataSource localDataSource){
        if (INSTANCE == null){
            INSTANCE = new QuestionRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    /**
     * Usado para forzar a {@link #getInstance(QuestionDataSource, QuestionDataSource)} a crear unanueva INSTANCE
     * la siguiente vez que sea llamado.
     */
    public static void destroyInstance(){
        QuestionRemoteDataSource.destroyInstance();
        QuestionLocalDataSource.destroyInstance();
        INSTANCE = null;
    }

    @Override
    public void getQuestions(final LoadQuestionsCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedQuestions != null && !mCacheIsDirty) {
            callback.onQuestionsLoaded(Lists.newArrayList(mCachedQuestions.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getQuestionsFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mLocalDataSource.getQuestions(new LoadQuestionsCallback() {
                @Override
                public void onQuestionsLoaded(List<Questions> questions) {
                    refreshCache(questions);
                    callback.onQuestionsLoaded(Lists.newArrayList(mCachedQuestions.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getQuestionsFromRemoteDataSource(callback);
                }
            });
        }

    }

    @Override
    public void deleteAllQuestions() {
        mLocalDataSource.deleteAllQuestions();
        mRemoteDataSource.deleteAllQuestions();

        if (mCachedQuestions == null) {
            mCachedQuestions = LinkedHashMultimap.create();
        }
        mCachedQuestions.clear();
    }

    @Override
    public void saveQuestion(Questions question) {
        checkNotNull(question);
        mRemoteDataSource.saveQuestion(question);
        mLocalDataSource.saveQuestion(question);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedQuestions == null) {
            mCachedQuestions = LinkedHashMultimap.create();
        }
        mCachedQuestions.put(question.getId(), question);
    }

    @Override
    public void getRandomQuestion(LoadRandomQuestionCallback callback) {

        if (!canContinue){
            callback.endQuestionsGame();
            return;
        }

        if (mCachedQuestions != null){
            if (mQuestionsGame == null){
                mQuestionsGame = HashMultimap.create();
                mQuestionsGame.putAll(mCachedQuestions);
            }

            Object[] values = mQuestionsGame.values().toArray();
            Object randomQuestion = values[new Random().nextInt(values.length)];
            Questions question = (Questions)randomQuestion;

            Print.i(TAG, "the key is = ["+ question.getId() + "] and her value is = ["+ question.getQuestion() +"]");
            mQuestionsGame.removeAll(question.getId());
            if (mQuestionsGame.isEmpty()){
                canContinue = false;
            }
            callback.onQuestionLoaded(question);
        }else{
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void sumatePoint(int gamming, SumatePointCallback callback) {
        mRemoteDataSource.sumatePoint(gamming, callback);
    }

    private void getQuestionsFromRemoteDataSource(final LoadQuestionsCallback callback) {
        mRemoteDataSource.getQuestions(new LoadQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Questions> questions) {
                refreshCache(questions);
                refreshLocalDataSource(questions, callback);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Questions> questions, LoadQuestionsCallback callback) {
        mLocalDataSource.deleteAllQuestions();
        new SaveDataToSQLite(Constants.QUESTIONS, questions, callback).execute();
    }

    private void refreshCache(List<Questions> questions) {
        if (mCachedQuestions == null) {
            mCachedQuestions = LinkedHashMultimap.create();
        }
        mCachedQuestions.clear();
        for (Questions question : questions) {
            mCachedQuestions.put(question.getId(), question);
        }
        mCacheIsDirty = false;
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveDataToSQLite extends AsyncTask<Object, Void, Boolean>{

        private String type;
        private final List list;
        private final LoadQuestionsCallback callback;

        SaveDataToSQLite(String type, List list, LoadQuestionsCallback callback) {
            this.type = type;
            this.list = list;
            this.callback = callback;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Boolean doInBackground(Object... objects) {

            switch (type){
                case Constants.QUESTIONS:
                    for (Questions question : (List<Questions>)list) {
                        mLocalDataSource.saveQuestion(question);
                    }
                    return true;
                case Constants.HONOR:
                    break;
                default:

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            switch (type){
                case Constants.QUESTIONS:
                    callback.onQuestionsLoaded(Lists.newArrayList(mCachedQuestions.values()));
                    break;
                case Constants.HONOR:
                    break;
                default:

            }
        }
    }
}
