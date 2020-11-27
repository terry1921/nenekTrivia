package com.fenixarts.nenektrivia.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fenixarts.nenektrivia.data.source.QuestionDataSource;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;
import com.fenixarts.nenektrivia.data.source.remote.RemoteDataSource;
import com.fenixarts.nenektrivia.game.domain.models.Questions;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 26/01/18 - 11:36.
 */

public class QuestionLocalDataSource implements QuestionDataSource {

    private static final String TAG = "QuestionLocalDataSource";
    private static QuestionLocalDataSource INSTANCE = null;
    private final Gson gson;
    private SQLite sqLite;

    // Constructor privado para prevenir la creacion de instancia directa
    private QuestionLocalDataSource(@NonNull final Context context) {
        checkNotNull(context);
        sqLite = new SQLite(context);
        gson = new Gson();
    }

    /**
     * Regresa una sola instancia de esta clase, la crea si es necesario
     *
     * @param context contexto
     * @return la instancia de {@link RemoteDataSource}
     */
    public static QuestionLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuestionLocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Usado para forzar a {@link #getInstance(Context)} a crear una nueva INSTANCE
     * la siguiente vez que sea llamado.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Not required because the {@link QuestionRepository} handles the logic of refreshing the
     * question from all the available data sources.
     */
    @Override
    public void refreshTasks() {
        // empty
    }

    @Override
    public void getQuestions(LoadQuestionsCallback callback) {
        JSONArray dataList = null;
        try {
            sqLite.open();
            dataList = sqLite.printList(sqLite.selectAllQuestions());
            sqLite.close();
        } catch (JSONException e) {
            Print.e(TAG, e.getMessage(), e.getCause());
        }

        if (dataList != null && dataList.length() > 0){
            Questions[] items = gson.fromJson(dataList.toString(), Questions[].class);
            callback.onQuestionsLoaded(Lists.newArrayList(items));
        }else{
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void deleteAllQuestions() {
        int delete = sqLite.deleteAllQuestions();
        Print.d(TAG, String.format(Locale.US,"deletes items = [%d]", delete));
    }

    @Override
    public void saveQuestion(Questions question) {
        checkNotNull(question);

        long insert = sqLite.insertQuestion(question);

        Print.d(TAG, String.format(Locale.US,"insert items = [%d]", insert));
    }

    @Override
    public void getRandomQuestion(LoadRandomQuestionCallback callback) {
        // logic in remote
    }

    @Override
    public void sumatePoint(int gamming, SumatePointCallback callback) {
        // logic in remote
    }
}
