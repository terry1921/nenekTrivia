package com.fenixarts.nenektrivia.login;

import android.app.Activity;
import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * NenekTrivia
 * Created by terry0022 on 08/01/18 - 12:30.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";
    @NonNull
    private final LoginContract.View mView;
    @NonNull
    private final Activity mControler;

    LoginPresenter(@NonNull final LoginContract.View view, @NonNull final Activity ownerControler) {
        mView = view;
        mControler = ownerControler;
    }

    @Override
    public void onStart() {

    }

    /**
     * Manejo del access token de facebook
     * @param mAuth FirebaseAuth
     * @param accessToken objeto
     */
    @Override
    public void handleFacebookAccessToken(final FirebaseAuth mAuth, AccessToken accessToken) {
        Print.i(TAG, "handleFacebookAccessToken:" + accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        authSignIn(mAuth, credential);
    }

    @Override
    public void handleTwitterSession(final FirebaseAuth mAuth, final TwitterSession session) {
        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Do something with the result, which provides the email address
                Print.d(TAG, "handleTwitterSession:success");
                mAuthSessionPair(mAuth, session, result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Print.w(TAG, "handleTwitterSession:failure", exception.getCause());
                mView.updateUI(String.format("Authentication failure.\n%s", exception.getMessage()));
            }
        });
    }

    @Override
    public void handleGoogleSignInResult(final FirebaseAuth mAuth, final Task<GoogleSignInAccount> accountTask) {
        try {
            GoogleSignInAccount account = accountTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(mAuth, account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Print.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            mView.updateUI(String.format("Authentication failed.\n%s", e.getMessage()));
        }
    }

    private void firebaseAuthWithGoogle(final FirebaseAuth mAuth, final GoogleSignInAccount acct) {
        Print.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        authSignIn(mAuth, credential);
    }

    private void mAuthSessionPair(final FirebaseAuth mAuth, final TwitterSession session, final String mail) {
        AuthCredential credential = TwitterAuthProvider.getCredential(session.getAuthToken().token, session.getAuthToken().secret);
        mAuth.signInWithCredential(credential).addOnCompleteListener(mControler, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Print.d(TAG, "mAuthSessionPair:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    configurateMail(user, mail);
                } else {
                    // If sign in fails, display a message to the user.
                    Exception exception = task.getException();
                    assert exception != null;
                    Print.w(TAG, "mAuthSessionPair:failure", exception.getCause());
                    mView.updateUI(String.format("Authentication failed.\n%s", exception.getMessage()));
                }
            }
        });
    }

    private void configurateMail(final FirebaseUser user, String mail) {
        user.updateEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Print.d(TAG, "configurateMail:onComplete");
                    mView.updateUI(user);
                }else {
                    // If sign in fails, display a message to the user.
                    Exception exception = task.getException();
                    assert exception != null;
                    Print.w(TAG, "configurateMail:failure", exception.getCause());
                    mView.updateUI(String.format("Authentication failed.\n%s", exception.getMessage()));
                }
            }
        });
    }

    private void authSignIn(final FirebaseAuth mAuth, AuthCredential credential) {
        Preconditions.checkNotNull(mAuth, "FirebaseAuth can't be null");
        Preconditions.checkNotNull(credential, "AuthCredential can't be null");

        mAuth.signInWithCredential(credential).addOnCompleteListener(mControler, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Print.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    mView.updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Exception exception = task.getException();
                    assert exception != null;
                    Print.w(TAG, "signInWithCredential:failure", exception.getCause());
                    mView.updateUI(String.format("Authentication failed.\n%s", exception.getMessage()));
                }
            }
        });
    }

}