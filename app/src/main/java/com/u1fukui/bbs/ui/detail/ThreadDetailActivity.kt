package com.u1fukui.bbs.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.u1fukui.bbs.R
import com.u1fukui.bbs.api.ThreadApi
import com.u1fukui.bbs.databinding.ActivityThreadDetailBinding
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.paging.comment.CommentPagedListAdapter
import com.u1fukui.bbs.repository.ThreadRepository
import com.u1fukui.bbs.ui.BaseActivity
import javax.inject.Inject

class ThreadDetailActivity : BaseActivity() {

    @Inject
    lateinit var navigator: ThreadDetailNavigator

    @Inject
    lateinit var threadApi: ThreadApi

    private val adapter = CommentPagedListAdapter()

    private val repository: ThreadRepository by lazy {
        ThreadRepository(threadApi)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityThreadDetailBinding>(
            this,
            R.layout.activity_thread_detail
        )
    }

    private val bbsThread by lazy {
        intent.getSerializableExtra(EXTRA_THREAD) as BbsThread
    }

    private val viewModel: ThreadDetailViewModel by lazy {
        ViewModelProviders
            .of(
                this, ThreadDetailViewModel.Factory(
                    this,
                    bbsThread,
                    repository,
                    navigator
                )
            )
            .get(ThreadDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar(binding.toolbar, true)
        initViews()

        viewModel.commentBindingModelList.observe(this, Observer {
            adapter.submitList(it)
        })
        binding.viewModel = viewModel
    }

    private fun initViews() {
        binding.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    companion object {

        private const val EXTRA_THREAD = "extra.thread"

        @JvmStatic
        fun createIntent(context: Context, thread: BbsThread) =
            Intent(context, ThreadDetailActivity::class.java).apply {
                putExtra(EXTRA_THREAD, thread)
            }
    }
}
