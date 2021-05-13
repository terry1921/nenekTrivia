package com.fenixarts.nenektrivia.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.base.BaseActivity;
import com.fenixarts.nenektrivia.main.MainActivity;
import com.fenixarts.nenektrivia.utils.Notify;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * NenekTrivia
 * Created by terry0022 on 08/01/18 - 10:30.
 *  - Facebook login
 *  - Twitter login
 */
public class Login extends BaseActivity implements LoginContract.View {

    private static final String TAG = "Login";
    private static final int RC_SIGN_IN = 123;
    private LoginButton loginFbButton;
    private CallbackManager mCallbackManager;
    private TwitterLoginButton loginTwButton;
    private LoginContract.Presenter presenter;
    private GoogleSignInClient mGoogleSignInClient;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNenekView(savedInstanceState, R.layout.activity_login);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and updateCollection UI accordingly.
        FirebaseUser currentUser = getAuth().getCurrentUser();
        updateUI(currentUser);
    }

    private void initView() {
        /* init views */
        loginTwButton = findViewById(R.id.login_twitter_button);
        loginFbButton = findViewById(R.id.login_fb_button);

        /* init presenter */
        presenter = new LoginPresenter(this, this);

        /* init API buttons */
        initGoogleButton();
        initTwitterButton();
        initFacebookButton();

    }

    private void initGoogleButton() {
        // Initialize Google Button
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton mLoginGoogleButton = findViewById(R.id.login_google_button);
        mLoginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void initFacebookButton() {
        // Initialize Facebook Button
        mCallbackManager = CallbackManager.Factory.create();
        loginFbButton.setReadPermissions("email", "public_profile");
        loginFbButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                presenter.handleFacebookAccessToken(getAuth(), loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Print.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Print.w(TAG, "facebook:onError:", error);
                updateUI(getString(R.string.error_login_fb));
            }
        });
        loginFbButton.setText(R.string.login);
    }

    private void initTwitterButton() {
        //Initialize Twitter Button
        loginTwButton.setText(R.string.login);
        loginTwButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Print.d(TAG, "twitterLogin:success" + result);
                presenter.handleTwitterSession(getAuth(), result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Print.w(TAG, "twiter:failure:", exception.getCause());
                updateUI(getString(R.string.error_login_tw));
            }
        });
    }

    @Override
    public void updateUI(FirebaseUser currentUser) {
        if (null != currentUser) {
            Print.i("updateUI:OK Session");
            Print.d(String.format("email=[%s] - userId=[%s]", currentUser.getEmail(),currentUser.getUid()));
            startActivity(new Intent(this,MainActivity.class));
            finish();
        } else {
            Print.i("updateUI:NO Session");
        }
    }

    @Override
    public void updateUI(String message) {
        new Notify(this, Notify.LENGTH_SHORT, message, Notify.NOTIFY_ALERT).showNotify();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            presenter.handleGoogleSignInResult(getAuth(), task);
        }else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
            loginTwButton.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
