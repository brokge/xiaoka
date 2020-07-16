package com.yusuzi.xiaoka.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.EnumHandleType;
import com.yusuzi.xiaoka.injection.Injections;
import com.yusuzi.xiaoka.persistence.Card;
import com.yusuzi.xiaoka.ui.ViewModelFactory;
import com.yusuzi.xiaoka.ui.card.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.CompositeDisposable;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private TextView     mCountView;
    private TextView     mAddView;
    private ImageView    mEmptyView;
    private RecyclerView mRecyclerView;

    private CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        //dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        ViewModelFactory mViewModelFactory = Injections.provideViewModelFactory(getContext());
        dashboardViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mCountView = root.findViewById(R.id.tv_card_count);
        mAddView = root.findViewById(R.id.tv_add_card);
        mRecyclerView = root.findViewById(R.id.rv_card_list);
        mEmptyView = root.findViewById(R.id.iv_empty_view);
        return root;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                DetailActivity.start(getContext(), "", EnumHandleType.ADD);
            }
        });
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);
        dashboardViewModel.getCardListLiveData().observe(getViewLifecycleOwner(),
                content -> {
                    SimpleItemRecyclerViewAdapter adapter  = (SimpleItemRecyclerViewAdapter) mRecyclerView.getAdapter();
                    List<Card>                    cardList = content.getCardList();
                    mCountView.setText(String.format(getString(R.string.str_format_count), cardList.size()));
                    adapter.setItems(cardList);
                    mEmptyView.setVisibility(cardList.isEmpty() ? View.VISIBLE : View.GONE);
                });

        setupRecyclerView(mRecyclerView);
        mDisposable.add(dashboardViewModel.getCardList());
    }

    @Override
    public void onResume () {
        super.onResume();
        mDisposable.add(dashboardViewModel.getCardList());
    }

    private void setupRecyclerView (@NonNull RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        SimpleItemRecyclerViewAdapter adapter = new SimpleItemRecyclerViewAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop () {
        super.onStop();
        mDisposable.clear();
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<Card> mValues;
        private Context    mContext;

        public SimpleItemRecyclerViewAdapter (List<Card> items, Context context) {
            mValues = items;
            mContext = context;
        }

        public void setItems (List<Card> items) {
            mValues = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder (final ViewHolder holder, int position) {
            Card card = mValues.get(position);
            holder.idName.setText(card.cardName);
            String cardNum = card.cardNum;
            if(cardNum.length() > 4) {
                cardNum = cardNum.substring(cardNum.length() - 4, cardNum.length() );
            }
            holder.tvCardNum.setText(String.format("****  ****  ****  %s", cardNum));
            holder.tvCardBillDate.setText(String.format(mContext.getString(R.string.str_format_billdate), card.cardBillDate));
            holder.tvCardRepayment.setText(String.format(mContext.getString(R.string.str_format_repayment), card.cardRepaymentDate));
            holder.tvCardAmount.setText(String.valueOf(card.cardAmount));
            //holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    DetailActivity.start(mContext, card.cid, EnumHandleType.DETAIL);
                }
            });
        }

        @Override
        public int getItemCount () {
            return mValues.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            View     vBgItem;
            TextView idName;
            TextView tvCardAmount;
            TextView tvCardNum;
            TextView tvCardBillDate;
            TextView tvCardRepayment;

            ViewHolder (View rootView) {
                super(rootView);
                vBgItem = rootView.findViewById(R.id.v_bg_item);
                idName = rootView.findViewById(R.id.id_name);
                tvCardAmount = rootView.findViewById(R.id.tv_card_amount);
                tvCardNum = rootView.findViewById(R.id.tv_card_num);
                tvCardBillDate = rootView.findViewById(R.id.tv_card_bill_date);
                tvCardRepayment = rootView.findViewById(R.id.tv_card_repayment);
            }
        }
    }

}