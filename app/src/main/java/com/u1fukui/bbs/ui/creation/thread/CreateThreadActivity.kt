package com.u1fukui.bbs.ui.creation.thread

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.u1fukui.bbs.R
import com.u1fukui.bbs.databinding.ActivityCreateThreadBinding
import com.u1fukui.bbs.ui.BaseActivity

class CreateThreadActivity : BaseActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityCreateThreadBinding>(this, R.layout.activity_create_thread)
    }

    val fragmentContainerId = R.id.fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar(binding.toolbar, true)
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
