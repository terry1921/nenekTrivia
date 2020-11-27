package com.fenixarts.nenektrivia.game;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fenixarts.nenektrivia.R;

/**
 * NenekTrivia
 * Created by terry0022 on 10/02/18 - 08:22.
 */

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class DialogLose extends Dialog implements View.OnClickListener{

    private TextView mTitleView;
    private TextView mTotalPointsView;
    private String mTitleText;
    private String mPointsText;
    private String mCancelText;
    private String mConfirmText;
    private OnClickListener onAcceptClickListener = null;
    private OnClickListener onCancelClickListener = null;
    private Button mBtnAcceptView;
    private Button mBtnCancelView;

    /**
     * OnClickListener
     */
    @FunctionalInterface
    public interface OnClickListener {
        /**
         * onClick
         * @param view view
         */
        void onClick(DialogLose view);
    }

    DialogLose(@NonNull Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_lose);
        initView();
    }

    private void initView() {
        mTitleView = findViewById(R.id.title);
        mTotalPointsView = findViewById(R.id.total_points);
        mBtnAcceptView = findViewById(R.id.btn_accept);
        mBtnAcceptView.setOnClickListener(DialogLose.this);
        mBtnCancelView = findViewById(R.id.btn_cancel);
        mBtnCancelView.setOnClickListener(DialogLose.this);


        setTitle(mTitleText);
        setPoints(mPointsText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);

    }

    public DialogLose setTitle(final String title){
        this.mTitleText = title;
        if (mTitleView != null && mTitleText != null) {
            mTitleView.setText(mTitleText);
        }
        return this;
    }

    public DialogLose setPoints(String points) {
        mPointsText = points;
        if (mTotalPointsView != null && mPointsText != null){
            mTotalPointsView.setText(mPointsText);
        }
        return this;
    }

    public DialogLose setConfirmText(String confirmText) {
        mConfirmText = confirmText;
        if (mBtnAcceptView !=null && mConfirmText != null){
            mBtnAcceptView.setText(mConfirmText);
            mBtnAcceptView.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public DialogLose setCancelText(String cancelText) {
        mCancelText = cancelText;
        if (mBtnCancelView !=null && mCancelText != null){
            mBtnCancelView.setText(mCancelText);
            mBtnCancelView.setVisibility(View.VISIBLE);
        }
        return this;
    }

    @SuppressWarnings("unused")
    public DialogLose setCancelClickListener (OnClickListener listener) {
        onCancelClickListener = listener;
        return this;
    }

    public DialogLose setConfirmClickListener(OnClickListener listener) {
        onAcceptClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_accept) {
            if (onAcceptClickListener != null){
                onAcceptClickListener.onClick(DialogLose.this);
            }else {
                dismiss();
            }
        } else if (view.getId() == R.id.btn_cancel) {
            if (onCancelClickListener != null){
                onCancelClickListener.onClick(DialogLose.this);
            }else{
                dismiss();
            }
        }

    }
}
