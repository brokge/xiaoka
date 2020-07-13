package com.yusuzi.xiaoka.ui.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.EnumHandleType;
import com.yusuzi.xiaoka.common.Constants;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;


public class DetailActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navController = Navigation.findNavController(this, R.id.detail_nav_host_fragment);
        if(savedInstanceState == null) {
            Bundle arguments = getIntent().getExtras();
            EnumHandleType mHandleType = EnumHandleType.getType(
                    getIntent().getIntExtra(Constants.KEY_HANDLE_TYPE, 2));

            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.detail_navigation);

            if(mHandleType == EnumHandleType.DETAIL) {
                actionBar.setTitle(getString(R.string.str_detail));
                navGraph.setStartDestination(R.id.cardDetailFragment);
                navController.setGraph(navGraph, arguments);
            } else {
                actionBar.setTitle(getString(R.string.str_add));
                navGraph.setStartDestination(R.id.cardEditFragment);
                navController.setGraph(navGraph);
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            if(!navController.navigateUp()) {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp () {
        return navController.navigateUp();
    }

    public static void start (Context context, String id, EnumHandleType type) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra(Constants.KEY_ID, id);
        starter.putExtra(Constants.KEY_HANDLE_TYPE, type.getValue());
        context.startActivity(starter);
    }
}