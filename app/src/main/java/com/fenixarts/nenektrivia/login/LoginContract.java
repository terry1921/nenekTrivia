package com.fenixarts.nenektrivia.login;

import com.facebook.AccessToken;
import com.fenixarts.nenektrivia.base.BasePresenter;
import com.fenixarts.nenektrivia.base.BaseView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * NenekTrivia
 * Created by terry0022 on 08/01/18 - 12:30.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void updateUI(final FirebaseUser user);

        void updateUI(final String message);

    }

    interface Presenter extends BasePresenter{

        void handleFacebookAccessToken(final FirebaseAuth mAuth, final AccessToken accessToken);

        void handleTwitterSession(final FirebaseAuth mAuth, final TwitterSession session);

        void handleGoogleSignInResult(final FirebaseAuth mAuth, final Task<GoogleSignInAccount> accountTask);

    }

}
