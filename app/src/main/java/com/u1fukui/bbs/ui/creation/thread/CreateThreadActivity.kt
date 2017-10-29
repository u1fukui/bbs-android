package com.u1fukui.bbs.ui.creation.thread

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.IdRes

import com.u1fukui.bbs.R
import com.u1fukui.bbs.databinding.ActivityCreateThreadBinding
import com.u1fukui.bbs.ui.BaseActivity

class CreateThreadActivity : BaseActivity() {

    private var binding: ActivityCreateThreadBinding? = null

    val fragmentContainerId: Int
        @IdRes
        get() = R.id.fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_thread)

        initToolbar(binding!!.toolbar, true)
        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            return
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SelectCategoryFragment.newInstance())
                .commit()
    }
}
