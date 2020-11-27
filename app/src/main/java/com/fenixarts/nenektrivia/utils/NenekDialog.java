package com.fenixarts.nenektrivia.utils;

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
 * Created by terry0022 on 22/02/18 - 12:10.
 */

@SuppressWarnings("UnusedReturnValue")
public class NenekDialog extends Dialog implements View.OnClickListener {

    private TextView mTitleView;
    private TextView mContentView;
    private Button mAcceptBtn;
    private Button mCancelBtn;
    private OnClickListener onAcceptClickListener = null;
    private OnClickListener onCancelClickListener = null;
    private String mTitleText;
    private String mContentText;
    private String mAcceptText;
    private String mCancelText;

    /**
     * OnClickListener
     */
    @FunctionalInterface
    public interface OnClickListener {
        /**
         * onClick
         *
         * @param view view
         */
        void onClick(NenekDialog view);
    }

    public NenekDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.nenek_dialog);
        initView();
    }

    private void initView() {
        mTitleView = findViewById(R.id.title);
        mContentView = findViewById(R.id.content);
        mAcceptBtn = findViewById(R.id.btn_accept);
        mAcceptBtn.setOnClickListener(NenekDialog.this);
        mCancelBtn = findViewById(R.id.btn_cancel);
        mCancelBtn.setOnClickListener(NenekDialog.this);

        setTitle(mTitleText);
        setContent(mContentText);
        setAcceptText(mAcceptText);
        setCancelText(mCancelText);
    }

    public NenekDialog setTitle(final String title){
        this.mTitleText = title;
        if (mTitleView != null && mTitleText != null) {
            mTitleView.setText(mTitleText);
        }
        return this;
    }

    public NenekDialog setContent(final String content){
        this.mContentText = content;
        if (mContentView != null && mContentText != null) {
            mContentView.setText(mContentText);
        }
        return this;
    }

    public NenekDialog setAcceptText(String label) {
        mAcceptText = label;
        if (mAcceptBtn !=null && mAcceptText != null){
            mAcceptBtn.setText(mAcceptText);
            mAcceptBtn.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public NenekDialog setCancelText(String label) {
        mCancelText = label;
        if (mCancelBtn !=null && mCancelText != null){
            mCancelBtn.setText(mCancelText);
            mCancelBtn.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public NenekDialog setCancelClickListener (OnClickListener listener) {
        onCancelClickListener = listener;
        return this;
    }

    public NenekDialog setAcceptClickListener(OnClickListener listener) {
        onAcceptClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_accept) {
            if (onAcceptClickListener != null){
                onAcceptClickListener.onClick(NenekDialog.this);
            }else {
                dismiss();
            }
        } else if (view.getId() == R.id.btn_cancel) {
            if (onCancelClickListener != null){
                onCancelClickListener.onClick(NenekDialog.this);
            }else{
                dismiss();
            }
        }
    }

}
