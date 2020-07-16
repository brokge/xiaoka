package com.yusuzi.xiaoka.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.EnumHandleType;
import com.yusuzi.xiaoka.ui.setting.SettingActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NotificationsFragment extends Fragment {

    protected View                   rootView;
    protected SwitchCompat           scAmount;
    protected SwitchCompat           scBillDate;
    protected TextView               tvFeedback;
    protected TextView               tvAbout;
    protected TextView               tvVersion;
    private   NotificationsViewModel notificationsViewModel;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged (@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    private void bindView (View rootView) {
        scAmount = rootView.findViewById(R.id.sc_amount);
        scBillDate = rootView.findViewById(R.id.sc_bill_date);
        tvFeedback = rootView.findViewById(R.id.tv_feedback);
        tvAbout = rootView.findViewById(R.id.tv_about);
        tvVersion = rootView.findViewById(R.id.tv_version);
        tvAbout.setOnClickListener(v -> SettingActivity.start(getContext(), EnumHandleType.ABOUT));
        tvFeedback.setOnClickListener(v -> SettingActivity.start(getContext(), EnumHandleType.FEEDBACK));

    }
}