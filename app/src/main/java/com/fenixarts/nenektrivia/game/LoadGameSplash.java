package com.fenixarts.nenektrivia.game;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.game.domain.models.Answers;
import com.fenixarts.nenektrivia.utils.Injection;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * NenekTrivia
 * Created by terry0022 on 10/02/18 - 07:13.
 */

public class LoadGameSplash extends AppCompatActivity implements GameContract.View {

    private static final int TIME_MILIS = 500;
    private GameContract.Presenter presenter;
    private ObjectAnimator anim;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_load_game_splash);

        initView();
    }

    private void initView() {

        presenter = new GamePresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetQuestionsUseCase(this),
                Injection.provideGetRandomQuestionUseCase(this),
                Injection.provideSumatePointUseCase(this),
                Injection.provideResetGameUseCase(this));

        View nenekIcon = findViewById(R.id.nenek_icon);
        anim = ObjectAnimator.ofFloat(nenekIcon, "rotation", 0, 360);
        anim.setDuration(2500);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.RESTART);

    }

    @Override
    protected void onStart() {
        super.onStart();
        anim.start();
        startSplash();
    }

    private void startSplash() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                presenter.onStart();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, TIME_MILIS);
    }

    @Override
    public void setPresenter(GameContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayQuestion(String question, List<Answers> answers) {
        //logic in game activity
    }

    @Override
    public void questionsLoaded() {
        //startActivity(new Intent(this, Game.class));
        startActivity(new Intent(this, Trivia.class));
        finish();
    }

    @Override
    public void onError() {
        //logic in game activity
    }

    @Override
    public void onError(String error) {
        //logic in game activity
    }

    @Override
    public void startedGame() {
        //logic in game activity
    }

    @Override
    public void pointSumate() {
        //logic in game activity
    }

    @Override
    public void resetSuccess() {
        //logic in game activity
    }

    @Override
    public void endQuestionsGame() {
        //logic in game activity
    }

    @Override
    public void endGame(int points) {
        //logic in game activity
    }
}
