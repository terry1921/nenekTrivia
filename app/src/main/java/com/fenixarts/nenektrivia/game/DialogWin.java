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
 * Created by terry0022 on 07/03/18 - 15:42.
 */

@SuppressWarnings("UnusedReturnValue")
public class DialogWin extends Dialog implements View.OnClickListener{

    private TextView mTitleView;
    private String mTitleText;
    private String mCancelText;
    private String mConfirmText;
    private DialogWin.OnClickListener onAcceptClickListener = null;
    private DialogWin.OnClickListener onCancelClickListener = null;
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
        void onClick(DialogWin view);

    }

    DialogWin(@NonNull Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_win);
        initView();
    }

    private void initView() {
        mTitleView = findViewById(R.id.title);
        mBtnAcceptView = findViewById(R.id.btn_accept);
        mBtnAcceptView.setOnClickListener(DialogWin.this);
        mBtnCancelView = findViewById(R.id.btn_cancel);
        mBtnCancelView.setOnClickListener(DialogWin.this);

        setTitle(mTitleText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);

    }

    public DialogWin setTitle(final String title){
        this.mTitleText = title;
        if (mTitleView != null && mTitleText != null) {
            mTitleView.setText(mTitleText);
        }
        return this;
    }

    DialogWin setConfirmText(String confirmText) {
        mConfirmText = confirmText;
        if (mBtnAcceptView !=null && mConfirmText != null){
            mBtnAcceptView.setText(mConfirmText);
            mBtnAcceptView.setVisibility(View.VISIBLE);
        }
        return this;
    }

    private DialogWin setCancelText(String cancelText) {
        mCancelText = cancelText;
        if (mBtnCancelView !=null && mCancelText != null){
            mBtnCancelView.setText(mCancelText);
            mBtnCancelView.setVisibility(View.VISIBLE);
        }
        return this;
    }

    @SuppressWarnings("unused")
    public DialogWin setCancelClickListener (DialogWin.OnClickListener listener) {
        onCancelClickListener = listener;
        return this;
    }

    DialogWin setConfirmClickListener(DialogWin.OnClickListener listener) {
        onAcceptClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_accept) {
            if (onAcceptClickListener != null){
                onAcceptClickListener.onClick(DialogWin.this);
            }else {
                dismiss();
            }
        } else if (view.getId() == R.id.btn_cancel) {
            if (onCancelClickListener != null){
                onCancelClickListener.onClick(DialogWin.this);
            }else{
                dismiss();
            }
        }

    }
}
