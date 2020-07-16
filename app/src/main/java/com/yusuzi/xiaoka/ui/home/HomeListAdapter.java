package com.yusuzi.xiaoka.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.bean.Item;
import com.yusuzi.xiaoka.bean.RecommendCardItem;
import com.yusuzi.xiaoka.common.EnumHandleType;
import com.yusuzi.xiaoka.ui.card.DetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<? extends Item> itemList;
    Context              mContext;

    public HomeListAdapter (Context context) {
        super();
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item_list_content, parent, false);
        return new ChildViewHolder(view);
    }

    public void setItemList (List<? extends Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        switch (item.getType()) {
            case ITEM_CHILD:
                RecommendCardItem recommendCardItem = (RecommendCardItem) item;
                ChildViewHolder childViewHolder = (ChildViewHolder) holder;
                childViewHolder.idName.setText(recommendCardItem.name);
                childViewHolder.tvRecommendAmount.setText(recommendCardItem.amount);
                childViewHolder.tvRecommendReason.setText(recommendCardItem.recommendReason);
                childViewHolder.itemView.setOnClickListener(v -> DetailActivity.start(mContext, recommendCardItem.getId(), EnumHandleType.DETAIL));
            case ITEM_GROUP:
        }
    }

    @Override
    public int getItemViewType (int position) {
        return itemList.get(position).getType().getValue();
    }

    @Override
    public int getItemCount () {
        return null == itemList ? 0 : itemList.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        protected TextView idName;
        protected TextView tvRecommendAmount;
        protected TextView tvRecommendReason;

        public ChildViewHolder (@NonNull View rootView) {
            super(rootView);
            idName = rootView.findViewById(R.id.id_name);
            tvRecommendAmount = rootView.findViewById(R.id.tv_recommend_amount);
            tvRecommendReason = rootView.findViewById(R.id.tv_distance_reason);
        }
    }
}
