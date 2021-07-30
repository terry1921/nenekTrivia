package com.fenixarts.nenektrivia.base;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.utils.App;

/**
 * NenekTrivia
 * Created by terry0022 on 17/02/18 - 09:09.
 */

public class BaseFragment extends Fragment {

    private ViewGroup mBaseContainerView;
    private View mMainView;
    private View mLoadingIndicatorView;
    private View mNoConnectionView;
    private View mNoContentView;

    public View onCreateNenekView(LayoutInflater inflater, ViewGroup container, int resourceLayout) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        initView(view);
        mBaseContainerView = view.findViewById(R.id.base_container);
        //noinspection ConstantConditions
        final LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        mMainView = layoutInflater.inflate(resourceLayout, mBaseContainerView, true);
        return view;
    }

    private void initView(View view) {
        /* Loading indicator */
        mLoadingIndicatorView = view.findViewById(R.id.loading_indicator);
        View nenekIcon = view.findViewById(R.id.nenek_icon);
        ObjectAnimator anim = ObjectAnimator.ofFloat(nenekIcon, "rotation", 0, 360);
        anim.setDuration(2500);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.start();

        /* no connection */
        mNoConnectionView = view.findViewById(R.id.no_connection);
        mNoContentView = view.findViewById(R.id.no_content);
    }

    protected void setNoConnection(boolean show){
        mNoConnectionView.setVisibility(show ? View.VISIBLE : View.GONE);
        mBaseContainerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    protected void setLoadingIndicator(boolean active) {
        if (active){
            mBaseContainerView.setVisibility(View.GONE);
            mLoadingIndicatorView.setVisibility(View.VISIBLE);
        }else{
            mBaseContainerView.startAnimation(AnimationUtils.loadAnimation(App.getContext(), R.anim.fade_in));
            mBaseContainerView.setVisibility(View.VISIBLE);
            mLoadingIndicatorView.startAnimation(AnimationUtils.loadAnimation(App.getContext(), R.anim.fade_out));
            mLoadingIndicatorView.setVisibility(View.GONE);
        }
    }

    protected void showNoAvailableContent(boolean show) {
        mNoContentView.setVisibility(show ? View.VISIBLE : View.GONE);
        mBaseContainerView.setVisibility(show ? View.GONE : View.VISIBLE);
        mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
    }


}
