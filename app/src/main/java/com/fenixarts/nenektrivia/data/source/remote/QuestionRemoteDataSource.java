package com.fenixarts.nenektrivia.data.source.remote;

import android.content.Context;
import androidx.annotation.NonNull;

import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.data.source.QuestionDataSource;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;
import com.fenixarts.nenektrivia.game.domain.models.Points;
import com.fenixarts.nenektrivia.game.domain.models.Questions;
import com.fenixarts.nenektrivia.utils.Constants;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 26/01/18 - 11:34.
 */

public class QuestionRemoteDataSource implements QuestionDataSource {

    private static final String TAG = "QuestionRemoteDataSourc";
    private static QuestionRemoteDataSource INSTANCE = null;
    private final DatabaseReference mDatabase;
    private FirebaseUser user;

    // Constructor privado para prevenir la creacion de instancia directa
    private QuestionRemoteDataSource(@NonNull final Context context) {
        checkNotNull(context);
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    /**
     * Regresa una sola instancia de esta clase, la crea si es necesario
     *
     * @param context contexto
     * @return la instancia de {@link RemoteDataSource}
     */
    public static QuestionRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuestionRemoteDataSource(context);
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
    public void getQuestions(final LoadQuestionsCallback callback) {
        Query mBestPlayerQuery = mDatabase.child(Constants.QUESTIONS);
        mBestPlayerQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Questions> questionsList = Lists.newArrayList();
                questionsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Questions item = snapshot.getValue(Questions.class);
                    questionsList.add(item);
                }
                callback.onQuestionsLoaded(Lists.newArrayList(questionsList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Print.e(TAG, databaseError.getMessage(), databaseError.toException());
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllQuestions() {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(String.format("/%s/", Constants.QUESTIONS), null);
        mDatabase.updateChildren(childUpdates);
    }

    @Override
    public void saveQuestion(Questions question) {
        mDatabase.child(Constants.QUESTIONS).child(question.getId()).setValue(question);
    }

    @Override
    public void getRandomQuestion(LoadRandomQuestionCallback callback) {

    }

    @Override
    public void sumatePoint(int gamming, SumatePointCallback callback) {
        checkNotNull(gamming);
        checkNotNull(callback, "Sumate Point Callback can't be null");
        switch (gamming){
            case Constants.STATUS_START_GAME: processGameStart(callback);
                break;
            case Constants.STATUS_GAMMING: processGame(callback);
                break;
            case Constants.STATUS_END_GAME: processEndGame(callback);
                break;
        }
    }

    private void processEndGame(final SumatePointCallback callback) {

        final int[] totalPoints = {0};

        mDatabase.child(Constants.POINTS).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Points points = dataSnapshot.getValue(Points.class);
                if (points != null) {
                    totalPoints[0] = points.getPoint();
                }

                processEndGame2(totalPoints[0], callback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Print.d(TAG, "processEndGame:onCancelled:" + databaseError);
                callback.onError();
            }
        });
    }

    private void processEndGame2(final int totalPoint, final SumatePointCallback callback) {
        mDatabase.child(Constants.HONOR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<BestPlayersItem> bestPlayersItems = Lists.newArrayList();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    bestPlayersItems.add(snapshot.getValue(BestPlayersItem.class));
                }

                String userId = user.getUid();
                boolean inBestPlayer = false;
                BestPlayersItem myPlayer = null;
                if (bestPlayersItems.isEmpty()) {
                    //noinspection ConstantConditions
                    BestPlayersItem item = new BestPlayersItem(user.getUid(),user.getPhotoUrl().toString(),user.getDisplayName(),totalPoint);
                    mDatabase.child(Constants.HONOR).child(user.getUid()).setValue(item);
                } else {
                    for (BestPlayersItem item : bestPlayersItems){
                        if (userId.equals(item.getId())){
                            inBestPlayer = true;
                            myPlayer = item;
                        }
                    }

                    if (inBestPlayer){
                        if (totalPoint >= myPlayer.getPoints()){
                            //noinspection ConstantConditions
                            BestPlayersItem item = new BestPlayersItem(user.getUid(),user.getPhotoUrl().toString(),user.getDisplayName(),totalPoint);
                            mDatabase.child(Constants.HONOR).child(user.getUid()).setValue(item);
                        }
                    }else {
                        //noinspection ConstantConditions
                        BestPlayersItem item = new BestPlayersItem(user.getUid(),user.getPhotoUrl().toString(),user.getDisplayName(),totalPoint);
                        mDatabase.child(Constants.HONOR).child(user.getUid()).setValue(item);
                    }
                }

                callback.onGameEnded(totalPoint);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });
    }

    private void processGame(final SumatePointCallback callback) {

        mDatabase.child(Constants.POINTS).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Points points = dataSnapshot.getValue(Points.class);

                if (points != null) {
                    int p = points.getPoint() + 1;
                    points.setPoint(p);
                }

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(user.getUid(),points);
                mDatabase.child(Constants.POINTS).updateChildren(childUpdates);
                callback.onSumatePoint();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });

    }

    private void processGameStart(SumatePointCallback callback) {

        String userId = "00";
        String displayName = "";
        String urlPhoto = "";

        if (user == null){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
        }

        if (user != null){
            displayName = user.getDisplayName();
            urlPhoto = user.getPhotoUrl().toString();
            userId = user.getUid();
        }

        Points points = new Points(displayName,urlPhoto,0);
        mDatabase.child(Constants.POINTS).child(userId).setValue(points);
        callback.onSumatePoint();
    }
}
