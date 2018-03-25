package com.u1fukui.bbs.ui.creation.comment


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.u1fukui.bbs.R
import com.u1fukui.bbs.databinding.ActivityCreateCommentBinding
import com.u1fukui.bbs.helper.DialogHelper
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.model.User
import com.u1fukui.bbs.repository.ThreadRepository
import com.u1fukui.bbs.ui.BaseActivity
import com.u1fukui.bbs.ui.Navigator

class CreateCommentActivity : BaseActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityCreateCommentBinding>(this, R.layout.activity_create_comment)
    }

    private val bbsThread: BbsThread by lazy {
        intent.getSerializableExtra(EXTRA_THREAD) as BbsThread
    }

    private val viewModel: CreateCommentViewModel by lazy {
        ViewModelProviders
                .of(this, CreateCommentViewModel.Factory(
                        bbsThread,
                        User(1L, "たろう"),
                        ThreadRepository(),
                        Navigator(this),
                        DialogHelper(this)
                ))
                .get(CreateCommentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initToolbar(binding.toolbar, true)
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    companion object {

        private val EXTRA_THREAD = "extra.thread"

        @JvmStatic
        fun createIntent(context: Context, thread: BbsThread) = Intent(context, CreateCommentActivity::class.java).apply {
            putExtra(EXTRA_THREAD, thread)
        }
    }
}
