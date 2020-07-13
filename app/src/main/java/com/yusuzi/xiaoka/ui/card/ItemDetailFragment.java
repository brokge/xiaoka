package com.yusuzi.xiaoka.ui.card;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.bean.CardContent;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private CardContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment () {
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = CardContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity                activity     = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if(appBarLayout != null) {
                appBarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimaryDark));
                appBarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if(mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        return rootView;
    }
}