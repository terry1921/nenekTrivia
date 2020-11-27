package com.fenixarts.nenektrivia.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fenixarts.nenektrivia.BuildConfig;
import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.game.LoadGameSplash;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * NenekTrivia
 * Created by terry0022 on 07/02/18 - 17:23.
 */

public class MainFragment extends Fragment {

    /**
     * Crea una instancia prefabricada de {@link MainFragment}
     *
     * @return Instancia dle fragmento
     */
    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        /* Initialize views */
        AdView mAdView = view.findViewById(R.id.adView);
        Button mPlayButton = view.findViewById(R.id.play_button);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startView(LoadGameSplash.class);
            }
        });

        /* set Actions */
        AdRequest adRequest = new AdRequest.Builder().build();
        if (!BuildConfig.DEBUG) mAdView.setAdUnitId(BuildConfig.LKD_1);
        mAdView.loadAd(adRequest);
    }

    private void startView(Class<?> activity) {
        startActivity(new Intent(getContext(),activity));
    }

}
