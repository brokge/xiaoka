package com.yusuzi.xiaoka.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yusuzi.xiaoka.R;

import androidx.annotation.NonNull;


public class LoadingDialog extends Dialog {

    protected ProgressBar loading;
    protected TextView    tvMessage;

    public LoadingDialog (@NonNull Context context, int themeResId) {
        super(context, themeResId);

        View mView = LayoutInflater.from(getContext()).inflate(R.layout.widget_loading_dialog, null);
        initView(mView);
        setContentView(mView);
    }

    public LoadingDialog (@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    private void initView (View rootView) {
        loading = rootView.findViewById(R.id.loading);
        tvMessage = rootView.findViewById(R.id.tv_message);
    }

    @Override
    protected void onStart () {
        loading.setVisibility(View.VISIBLE);
    }


    public void setMessage (String message) {
        tvMessage.setText(message);
    }

    public void setMessage (int resId) {
        tvMessage.setText(resId);
    }

    @Override
    protected void onStop () {
        super.onStop();
    }
}