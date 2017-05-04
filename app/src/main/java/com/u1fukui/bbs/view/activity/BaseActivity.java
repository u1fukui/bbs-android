package com.u1fukui.bbs.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    final void initToolbar(Toolbar toolbar, boolean canBack) {
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(toolbar.getTitle());
            if (canBack) {
                bar.setDisplayHomeAsUpEnabled(true);
                bar.setDisplayShowHomeEnabled(true);
                bar.setDisplayShowTitleEnabled(true);
                bar.setHomeButtonEnabled(true);
            }
        }
    }
}
