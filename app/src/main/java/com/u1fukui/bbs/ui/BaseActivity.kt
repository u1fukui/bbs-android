package com.u1fukui.bbs.ui

import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun initToolbar(toolbar: Toolbar, canBack: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = toolbar.title
            if (canBack) {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setDisplayShowTitleEnabled(true)
                setHomeButtonEnabled(true)
            }
        }
    }

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
