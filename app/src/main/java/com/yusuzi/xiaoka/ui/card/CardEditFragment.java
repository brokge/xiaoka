package com.yusuzi.xiaoka.ui.card;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.Constants;
import com.yusuzi.xiaoka.common.EnumResultState;
import com.yusuzi.xiaoka.common.WidgetValueCheckUtil;
import com.yusuzi.xiaoka.injection.Injections;
import com.yusuzi.xiaoka.persistence.Card;
import com.yusuzi.xiaoka.ui.ViewModelFactory;
import com.yusuzi.xiaoka.ui.widget.LoadingDialog;

import java.util.Calendar;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CardEditFragment extends Fragment {

    protected EditText etName;
    protected EditText etCardNum;
    protected EditText etAmount;
    protected EditText etBillDate;
    protected EditText etRepayment;
    protected TextView tvSave;

    private CardEditViewModel   mViewModel;
    private LoadingDialog       loadingDialog;
    private CompositeDisposable mDisposable = new CompositeDisposable();


    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        Toolbar  toolbar  = (Toolbar) activity.findViewById(R.id.detail_toolbar);
        if(toolbar != null) {
            toolbar.setTitle(getString(R.string.str_edit));
        }
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory mViewModelFactory = Injections.provideViewModelFactory(getContext());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(CardEditViewModel.class);
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
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).finish();
                return;
            }
            if(enumResultState == EnumResultState.FAIL) {
                Toast.makeText(getContext(), "保存异常", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(Constants.KEY_ID)) {
            String keyId = bundle.getString(Constants.KEY_ID);
            mViewModel.getCardMutableLiveData().observe(this, this::bindData);
            mDisposable.add(mViewModel.getCard(keyId));
        }
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_edit_fragment, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        bindView(view);
    }

    private void bindView (View rootView) {
        etName = (EditText) rootView.findViewById(R.id.et_name);
        etCardNum = (EditText) rootView.findViewById(R.id.et_card_num);
        etAmount = (EditText) rootView.findViewById(R.id.et_amount);
        etBillDate = (EditText) rootView.findViewById(R.id.et_bill_date);
        etRepayment = (EditText) rootView.findViewById(R.id.et_repayment);
        tvSave = (TextView) rootView.findViewById(R.id.tv_save);
        //etBillDate.setInputType(InputType.TYPE_NULL);
        //etBillDate.setOnClickListener(v -> showDateDialog());

        tvSave.setOnClickListener(v -> {
            Card card = mViewModel.getCardMutableLiveData().getValue();
            if(card == null) {
                card = new Card();
            }
            card.cardAmount = Integer.parseInt(WidgetValueCheckUtil.getEditValue(etAmount));
            card.cardName = WidgetValueCheckUtil.getEditValue(etName);
            card.cardNum = WidgetValueCheckUtil.getEditValue(etCardNum);
            card.cardBillDate = Integer.parseInt(WidgetValueCheckUtil.getEditValue(etBillDate));
            card.cardRepaymentDate = Integer.parseInt(WidgetValueCheckUtil.getEditValue(etRepayment));
            mDisposable.add(mViewModel.saveCard(card));
        });
    }

    private void bindData (Card card) {
        if(card == null) {
            return;
        }
        etName.setText(card.cardName);
        etCardNum.setText(card.cardNum);
        etAmount.setText(String.valueOf(card.cardAmount));
        etBillDate.setText(String.valueOf(card.cardBillDate));
        etRepayment.setText(String.valueOf(card.cardRepaymentDate));
    }

    private void showDateDialog () {
        new DatePickerDialog(getContext(),
                (view, year, month, day) -> {
                    // 更新EditText控件日期 小于10加0
                    etBillDate.setText(new StringBuilder()
                            .append(year)
                            .append(".")
                            .append((month + 1) < 10 ? "0"
                                    + (month + 1) : (month + 1))
                            .append(".")
                            .append((day < 10) ? "0" + day : day));
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    @Override
    public void onStop () {
        super.onStop();
        mDisposable.clear();
    }


}