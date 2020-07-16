package com.yusuzi.xiaoka.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.EnumResultState;
import com.yusuzi.xiaoka.common.WidgetValueCheckUtil;
import com.yusuzi.xiaoka.injection.Injections;
import com.yusuzi.xiaoka.ui.ViewModelFactory;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.CompositeDisposable;

public class HomeFragment extends Fragment {

    private EditText                  etAmount;
    private TextView                  tvComputeOk;
    private ContentLoadingProgressBar clpLoading;
    private TextView                  tvLoadingMsg;
    private Group                     groupLoading;
    private RecyclerView              rvCardList;
    private ImageView                 ivEmptyView;
    private TextView                  tvEmptyMsg;
    private Group                     groupEmpty;
    private HomeViewModel             homeViewModel;
    private CompositeDisposable       mDisposable = new CompositeDisposable();

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        bindView(root);
        return root;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewModelFactory mViewModelFactory = Injections.provideViewModelFactory(getContext());
        homeViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(HomeViewModel.class);
        homeViewModel.getLoadingState().observe(this, aBoolean -> groupLoading.setVisibility(aBoolean ? View.VISIBLE : View.GONE));
        homeViewModel.getResultMessage().observe(this, enumResultState -> {
            if(enumResultState == EnumResultState.SUCCESS) {
                Toast.makeText(getContext(), "计算成功", Toast.LENGTH_SHORT).show();
                return;
            }
            if(enumResultState == EnumResultState.FAIL) {
                Toast.makeText(getContext(), "计算异常，请重试", Toast.LENGTH_SHORT).show();
            }
        });
        homeViewModel.getListMutableLiveData().observe(this, items -> {
            if(items.size() > 0) {
                groupEmpty.setVisibility(View.GONE);
                HomeListAdapter homeListAdapter = (HomeListAdapter) rvCardList.getAdapter();
                homeListAdapter.setItemList(items);
            } else {
                groupEmpty.setVisibility(View.VISIBLE);
            }
        });
        setupRecyclerView(rvCardList);
    }

    private void setupRecyclerView (@NonNull RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        HomeListAdapter adapter = new HomeListAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }

    private void bindView (View rootView) {
        etAmount = rootView.findViewById(R.id.et_amount);
        tvComputeOk = rootView.findViewById(R.id.tv_compute_ok);
        clpLoading = rootView.findViewById(R.id.clp_loading);
        tvLoadingMsg = rootView.findViewById(R.id.tv_loading_msg);
        groupLoading = rootView.findViewById(R.id.group_loading);
        rvCardList = rootView.findViewById(R.id.rv_card_list);
        ivEmptyView = rootView.findViewById(R.id.iv_empty_view);
        tvEmptyMsg = rootView.findViewById(R.id.tv_empty_msg);
        groupEmpty = rootView.findViewById(R.id.group_empty);
        tvComputeOk.setOnClickListener(v -> {
            String amount = WidgetValueCheckUtil.getEditValue(etAmount);
            if(!TextUtils.isEmpty(amount)) {
                mDisposable.add(homeViewModel.compute(getContext(), Integer.valueOf(amount)));
            }
        });
    }

    @Override
    public void onStop () {
        super.onStop();
        mDisposable.clear();
    }
}