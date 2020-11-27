package com.fenixarts.nenektrivia.base;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.data.source.QuestionRepository;
import com.fenixarts.nenektrivia.data.source.Repository;
import com.fenixarts.nenektrivia.login.Login;
import com.fenixarts.nenektrivia.utils.Print;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;

/**
 * NenekTrivia
 * Created by terry0022 on 03/01/18 - 10:02.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private TextView toolbarSubtitle;
    private FirebaseUser user;

    /**
     * pintar vista en layout base
     * @param savedInstanceState bundle
     * @param resourceLayout layout to display
     */
    protected void onCreateNenekView(@Nullable final Bundle savedInstanceState, final int resourceLayout) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Initialize Twitter Button
        Twitter.initialize(this);

        /* set theme and baseview */
        setTheme(R.style.AppTheme);
        setContentView(R.layout.base_layout);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        ViewGroup mBaseContainer = findViewById(R.id.base_container);
        final LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        layoutInflater.inflate(resourceLayout, mBaseContainer, true);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        clearToolbarTexts();
    }

    protected void onCreateNenekBaseView(@Nullable final Bundle savedInstanceState, final int resourceLayout){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize Twitter Button
        Twitter.initialize(this);

        /* set theme and baseview */
        setTheme(R.style.AppTheme);
        setContentView(R.layout.base_layout);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        MobileAds.initialize(this, getString(R.string.admob_app_id));

        ViewGroup mBaseContainer = findViewById(R.id.base_container);
        View nenekIcon = findViewById(R.id.nenek_icon);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        clearToolbarTexts();

        ObjectAnimator anim = ObjectAnimator.ofFloat(nenekIcon, "rotation", 0, 360);
        anim.setDuration(2500);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.start();

        final LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        layoutInflater.inflate(resourceLayout, mBaseContainer, true);
    }

    protected void clearToolbarTexts() {
        toolbarTitle.setText("");
        toolbarSubtitle.setText("");
        toolbar.setVisibility(View.GONE);
        toolbarSubtitle.setVisibility(View.GONE);
        toolbarTitle.setVisibility(View.GONE);
    }

    protected void logout() {
        Print.d(TAG, "Close session");
        disconnectFromFacebook();
        disconnectFromTwitter();
        FirebaseAuth.getInstance().signOut();
        Repository.destroyInstance();
        QuestionRepository.destroyInstance();
        startActivity(new Intent(this, Login.class));
        finish();
    }

    private void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    private void disconnectFromTwitter(){
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        Twitter.getInstance().getActivityLifecycleManager().resetCallbacks();
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public void setAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseUser getUser() {
        return user;
    }

    @Override
    public void onBackPressed() {
        goBack();
        super.onBackPressed();
    }

    protected void goBack(){
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (upIntent != null){
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

}
