package com.u1fukui.bbs.ui.detail

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.u1fukui.bbs.R
import com.u1fukui.bbs.customview.BindingHolder
import com.u1fukui.bbs.customview.ObservableListRecyclerAdapter
import com.u1fukui.bbs.databinding.ActivityThreadDetailBinding
import com.u1fukui.bbs.databinding.ViewCommentCellBinding
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.repository.ThreadRepository
import com.u1fukui.bbs.ui.BaseActivity
import javax.inject.Inject

class ThreadDetailActivity : BaseActivity() {

    @Inject
    lateinit var navigator: ThreadDetailNavigator

    @Inject
    lateinit var repository: ThreadRepository

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityThreadDetailBinding>(this, R.layout.activity_thread_detail)
    }

    private val bbsThread by lazy {
        intent.getSerializableExtra(EXTRA_THREAD) as BbsThread
    }

    private val viewModel: ThreadDetailViewModel by lazy {
        ViewModelProviders
                .of(this, ThreadDetailViewModel.Factory(
                        bbsThread,
                        repository,
                        navigator
                ))
                .get(ThreadDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initToolbar(binding.toolbar, true)

        initViews()
        viewModel.start()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            adapter = Adapter(viewModel.commentBindingModelList)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    private class Adapter(list: ObservableList<CommentBindingModel>) : ObservableListRecyclerAdapter<CommentBindingModel, BindingHolder<ViewCommentCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder<ViewCommentCellBinding>(parent.context, parent, R.layout.view_comment_cell)

        override fun onBindViewHolder(holder: BindingHolder<ViewCommentCellBinding>, position: Int) {
            holder.binding?.apply {
                bindingModel = getItem(position)
                executePendingBindings()
            }
        }
    }

    companion object {

        private const val EXTRA_THREAD = "extra.thread"

        @JvmStatic
        fun createIntent(context: Context, thread: BbsThread) = Intent(context, ThreadDetailActivity::class.java).apply {
            putExtra(EXTRA_THREAD, thread)
        }
    }
}
