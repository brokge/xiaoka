package com.yusuzi.xiaoka.ui.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.Constants;
import com.yusuzi.xiaoka.common.EnumHandleType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class DetailHomeFragment extends Fragment {

    private EnumHandleType mHandleType = EnumHandleType.EDIT;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle parentArguments = getArguments();
        Bundle arguments       = new Bundle();

        arguments.putString(Constants.KEY_ID,
                parentArguments.getString(Constants.KEY_ID));

        mHandleType = EnumHandleType.getType(
                parentArguments.getInt(Constants.KEY_HANDLE_TYPE, 2));
        NavController navController = Navigation
                .findNavController(getActivity(), R.id.detail_nav_host_fragment);

        if(mHandleType == EnumHandleType.DETAIL) {
            //详情
            navController.navigate(R.id.action_home_to_cardDetailFragment, arguments);
        } else {
            navController.navigate(R.id.action_home_to_cardEditFragment);
        }
    }
}
