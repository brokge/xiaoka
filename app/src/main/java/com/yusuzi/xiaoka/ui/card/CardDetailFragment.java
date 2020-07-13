package com.yusuzi.xiaoka.ui.card;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.Constants;
import com.yusuzi.xiaoka.common.EnumResultState;
import com.yusuzi.xiaoka.injection.Injections;
import com.yusuzi.xiaoka.persistence.Card;
import com.yusuzi.xiaoka.ui.ViewModelFactory;
import com.yusuzi.xiaoka.ui.widget.LoadingDialog;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import io.reactivex.disposables.CompositeDisposable;


public class CardDetailFragment extends Fragment {

    protected TextView tvName;
    protected TextView tvCardNum;
    protected TextView etAmount;
    protected TextView etBillDate;
    protected TextView etRepayment;
    protected TextView tvEdit;
    protected TextView tvDel;

    private CardDetailViewModel mViewModel;
    private LoadingDialog       loadingDialog;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        Toolbar  toolbar  = (Toolbar) activity.findViewById(R.id.detail_toolbar);
        if(toolbar != null) {
            toolbar.setTitle(getString(R.string.str_detail));
        }
    }
    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory mViewModelFactory = Injections.provideViewModelFactory(getContext());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(CardDetailViewModel.class);
        mViewModel.getLoadingState().observe(this, isShow -> {
            if(loadingDialog == null) {
                loadingDialog = new LoadingDialog(Objects.requireNonNull(getContext()));
            }
            if(isShow) {
                loadingDialog.show();
            } else {
                loadingDialog.hide();
            }
        });
        mViewModel.getResultMessage().observe(this, enumResultState -> {
            if(enumResultState == EnumResultState.SUCCESS) {
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).finish();
                return;
            }
            if(enumResultState == EnumResultState.FAIL) {
                Toast.makeText(getContext(), "删除异常，请重试", Toast.LENGTH_SHORT).show();
            }
        });
        assert getArguments() != null;
        String keyId = getArguments().getString(Constants.KEY_ID);
        mViewModel.getCardMutableLiveData().observe(this, this::bindData);
        mDisposable.add(mViewModel.getCard(keyId));
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_detail_fragment, container, false);
        bindView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void bindView (View rootView) {
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvCardNum = (TextView) rootView.findViewById(R.id.tv_card_num);
        etAmount = (TextView) rootView.findViewById(R.id.et_amount);
        etBillDate = (TextView) rootView.findViewById(R.id.et_bill_date);
        etRepayment = (TextView) rootView.findViewById(R.id.et_repayment);
        tvEdit = (TextView) rootView.findViewById(R.id.tv_edit);
        tvDel = (TextView) rootView.findViewById(R.id.tv_del);
        tvEdit.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.detail_nav_host_fragment)
                    .navigate(R.id.action_detail_to_cardEditFragment,getArguments());
        });
        tvDel.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setMessage(getString(R.string.msg_del_confirm))
                    .setNegativeButton(getString(R.string.str_cancel), (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(getString(R.string.str_confirm), (dialog, which) ->
                            mDisposable.add(mViewModel.delCard(mViewModel.getCardMutableLiveData().getValue())))
                    .create()
                    .show();

        });
    }

    private void bindData (Card card) {
        tvName.setText(card.cardName);
        tvCardNum.setText(card.cardNum);
        etAmount.setText(String.valueOf(card.cardAmount));
        etBillDate.setText(String.format(getString(R.string.str_format_billdate), card.cardBillDate));
        etRepayment.setText(String.format(getString(R.string.str_format_repayment), card.cardRepaymentDate));
    }

    @Override
    public void onStop () {
        super.onStop();
        mDisposable.clear();
    }
}