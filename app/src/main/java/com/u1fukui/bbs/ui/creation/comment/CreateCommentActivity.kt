package com.u1fukui.bbs.ui.creation.comment


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

    private lateinit var viewModel: CreateCommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = createViewModel()
        binding.viewModel = viewModel
        initToolbar(binding.toolbar, true)
    }

    private fun createViewModel(): CreateCommentViewModel {
        val thread = intent.getSerializableExtra(EXTRA_THREAD) as BbsThread
        val user = User(1L, "たろう")
        val repository = ThreadRepository()
        val navigator = Navigator(this)
        val dialogHelepr = DialogHelper(this)
        return CreateCommentViewModel(thread, user, repository, navigator, dialogHelepr)
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
