package com.fenixarts.nenektrivia.game;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fenixarts.nenektrivia.BuildConfig;
import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.game.domain.adapters.QuestionAdapter;
import com.fenixarts.nenektrivia.game.domain.models.Answers;
import com.fenixarts.nenektrivia.main.MainActivity;
import com.fenixarts.nenektrivia.utils.Constants;
import com.fenixarts.nenektrivia.utils.Injection;
import com.fenixarts.nenektrivia.utils.NenekDialog;
import com.fenixarts.nenektrivia.utils.Notify;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;
import java.util.Locale;

/**
 * NenekTrivia
 * Created by terry0022 on 10/02/18 - 07:19.
 */

public class Trivia extends AppCompatActivity implements GameContract.View, QuestionAdapter.QuestionListener {

    private TextView mViewQuestion;
    private GameContract.Presenter presenter;
    private QuestionAdapter adapter;
    private Boolean isError = false;
    private View containerGame;
    private View mViewEndGame;
    private View answerCorrect;
    private View answerIncorrect;
    private View questionContainer;
    private RecyclerView recyclerView;
    private TextView mCounterView;
    private TextView mAnswerCounterView;
    private CountDownTimer countDownTimer;
    private static boolean isOnBackground = false;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Print.i("onCreate");
        isOnBackground = false;
        initView();

        mInterstitialAd = new InterstitialAd(this);
        if (BuildConfig.DEBUG) {
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id_debug));
        } else {
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id_release));
        }
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                presenter.onDestroy();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                presenter.onDestroy();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                presenter.onDestroy();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                presenter.onDestroy();
            }
        });
    }

    private void initView() {
        /* Initialize presenter*/
        presenter = new GamePresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetQuestionsUseCase(this),
                Injection.provideGetRandomQuestionUseCase(this),
                Injection.provideSumatePointUseCase(this),
                Injection.provideResetGameUseCase(this));

        mViewQuestion =  findViewById(R.id.question);
        containerGame = findViewById(R.id.container_game);
        mViewEndGame = findViewById(R.id.end_game);
        recyclerView = findViewById(R.id.answers);
        questionContainer = findViewById(R.id.question_container);
        answerCorrect = findViewById(R.id.answer_correct);
        answerIncorrect = findViewById(R.id.answer_incorrect);
        mCounterView = findViewById(R.id.counter);
        mAnswerCounterView = findViewById(R.id.answer_counter);

        /* set actions */
        adapter = new QuestionAdapter(this);
        adapter.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        countDownTimer = new CountDownTimer(21000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 6000){
                    mCounterView.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAlertPressed)));
                }
                mCounterView.setText(String.format(Locale.US, "%d", millisUntilFinished / 1000));
            }

            public void onFinish() {
                mCounterView.setText("0");
                presenter.endGame();
                presenter.sumatePoint(false);
            }

        };
    }

    @Override
    public void onBackPressed() {
        tryToGoBack();
    }

    private void tryToGoBack() {
        NenekDialog dialog = new NenekDialog(this);
        dialog.setTitle(getString(R.string.warning_exit));
        dialog.setContent(getString(R.string.content_exit));
        dialog.setCancelable(false);
        dialog.setCancelText(getString(R.string.popup_button_cancel));
        dialog.setCancelClickListener(new NenekDialog.OnClickListener() {
            @Override
            public void onClick(NenekDialog view) {
                view.dismiss();
            }
        });
        dialog.setAcceptText(getString(R.string.popup_button_accept));
        dialog.setAcceptClickListener(new NenekDialog.OnClickListener() {
            @Override
            public void onClick(NenekDialog view) {
                view.dismiss();
                presenter.onDestroy();
            }
        });
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Print.i("onPause");
        isOnBackground = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Print.i("onStart");
        newGame();
    }

    @Override
    public void setPresenter(GameContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClickQuestionListener(Answers question, View view, int correct) {
        view.setBackgroundResource(question.isCorrect() ? R.drawable.button_answer_game_correct : R.drawable.button_answer_game_incorrect);
        ((TextView)view.findViewById(R.id.answer)).setTextColor(Color.WHITE);
        countDownTimer.cancel();
        if (question.isCorrect()){
            showCorrectAction(question.isCorrect());
        }else{
            showIncorrectAction(question.isCorrect(), correct);
        }
    }

    private void newGame() {
        if (!isOnBackground){
            countDownTimer.cancel();
            presenter.startGame();
            presenter.sumatePoint();
            isOnBackground = false;
        }
    }

    private void anotherQuestion() {
        final DialogWin dialog = new DialogWin(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(Constants.EMPTY_STRING);
        dialog.setConfirmText(getString(R.string.next_question));
        dialog.setConfirmClickListener(new DialogWin.OnClickListener() {
            @Override
            public void onClick(DialogWin view) {
                view.dismiss();
                presenter.getRandomQuestion();
            }
        });
        if(!this.isFinishing()) {
            dialog.show();
        }else{
            presenter.getRandomQuestion();
        }
    }

    @Override
    public void displayQuestion(String question, List<Answers> answers) {
        showQuestion();
        mViewQuestion.setTextColor(Color.BLACK);
        mViewQuestion.setText(question);
        adapter.addCollection(answers);
        countDownTimer.start();
    }

    @Override
    public void questionsLoaded() {
        presenter.getRandomQuestion();
    }

    @Override
    public void onError() {
        if (!isError){
            presenter.setForceUpdate(true);
            presenter.onStart();
            isError = true;
        }else{
            new Notify(this, Notify.LENGTH_SHORT,getString(R.string.error_load_data),Notify.NOTIFY_ALERT).showNotify();
        }
    }

    @Override
    public void onError(String error) {
        new Notify(this, Notify.LENGTH_SHORT,error,Notify.NOTIFY_ALERT).showNotify();
    }

    @Override
    public void startedGame() {
        presenter.isGamming();
        presenter.getRandomQuestion();
        mAnswerCounterView.setText(R.string.dummy_zero);
    }

    @Override
    public void pointSumate() {
        presenter.isGamming();
        anotherQuestion();
    }

    @Override
    public void resetSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void endQuestionsGame() {
        containerGame.setVisibility(View.INVISIBLE);
        mViewEndGame.setVisibility(View.VISIBLE);
    }

    @Override
    public void endGame(int points) {
        String totalPoints;
        if (points < 10){
            totalPoints = "0" + points;
        }else{
            totalPoints = String.valueOf(points);
        }
        DialogLose dialog = new DialogLose(this);
        dialog.setTitle(getString(R.string.title_lose));
        dialog.setPoints(totalPoints);
        dialog.setConfirmText(getString(R.string.play_again));
        dialog.setConfirmClickListener(new DialogLose.OnClickListener() {
            @Override
            public void onClick(DialogLose view) {
                view.dismiss();
                newGame();
            }
        });
        dialog.setCancelText(getString(R.string.exit_dialog));
        dialog.setCancelClickListener(new DialogLose.OnClickListener() {
            @Override
            public void onClick(DialogLose view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    presenter.onDestroy();
                }
                view.dismiss();
            }
        });
        dialog.setCancelable(false);
        if(!this.isFinishing()) {
            dialog.show();
        }
    }

    private void showQuestion() {
        answerIncorrect.setVisibility(View.GONE);
        answerCorrect.setVisibility(View.GONE);
        questionContainer.setVisibility(View.VISIBLE);
    }

    private void showIncorrectAction(boolean isCorrect, int correct) {
        questionContainer.setVisibility(View.GONE);
        answerIncorrect.setVisibility(View.VISIBLE);
        View child = recyclerView.getChildAt(correct);
        if (child != null){
            child.setBackgroundResource(R.drawable.button_answer_game_correct);
            ((TextView)child.findViewById(R.id.answer)).setTextColor(Color.WHITE);
        }
        presenter.endGame();
        presenter.sumatePoint(isCorrect);
    }

    private void showCorrectAction(boolean isCorrect) {
        int total = Integer.parseInt(mAnswerCounterView.getText().toString());
        total += 1;
        mAnswerCounterView.setText(String.format(Locale.US,"%d",total));
        presenter.sumatePoint(isCorrect);
    }

}