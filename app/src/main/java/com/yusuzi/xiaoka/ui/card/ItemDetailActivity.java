package com.yusuzi.xiaoka.ui.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.EnumHandleType;


public class ItemDetailActivity extends AppCompatActivity {
    private EnumHandleType mHandleType = EnumHandleType.EDIT;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            Fragment fragment;
            if(mHandleType == EnumHandleType.DETAIL) {
                //详情
                fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);

            } else {
                fragment = new CardEditFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start (Context context) {
        Intent starter = new Intent(context, ItemDetailActivity.class);
        starter.putExtra(ItemDetailFragment.ARG_ITEM_ID, "1");
        context.startActivity(starter);
    }
}