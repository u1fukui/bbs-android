package com.u1fukui.bbs.ui.detail

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

    private var binding: ActivityThreadDetailBinding? = null

    private var adapter: Adapter? = null

    private var viewModel: ThreadDetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_thread_detail)

        val thread = intent.getSerializableExtra(EXTRA_THREAD) as BbsThread
        viewModel = ThreadDetailViewModel(thread, repository, navigator)
        binding!!.viewModel = viewModel
        initToolbar(binding!!.toolbar, true)

        initViews()
        viewModel!!.start()
    }

    private fun initViews() {
        adapter = Adapter(viewModel!!.commentViewModelList)
        binding!!.recyclerView.adapter = adapter
        binding!!.recyclerView.layoutManager = LinearLayoutManager(this)
        binding!!.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun onDestroy() {
        viewModel!!.destroy()
        binding!!.unbind()
        super.onDestroy()
    }

    private class Adapter(list: ObservableList<CommentViewModel>) : ObservableListRecyclerAdapter<CommentViewModel, BindingHolder<ViewCommentCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<ViewCommentCellBinding> {
            return BindingHolder(parent.context, parent, R.layout.view_comment_cell)
        }

        override fun onBindViewHolder(holder: BindingHolder<ViewCommentCellBinding>, position: Int) {
            val viewModel = getItem(position)
            val itemBinding = holder.binding
            itemBinding.viewModel = viewModel
            itemBinding.executePendingBindings()
        }
    }

    companion object {

        @JvmStatic
        val TAG = ThreadDetailActivity::class.java.simpleName

        private const val EXTRA_THREAD = "extra.thread"

        @JvmStatic
        fun createIntent(context: Context, thread: BbsThread): Intent {
            val intent = Intent(context, ThreadDetailActivity::class.java)
            intent.putExtra(EXTRA_THREAD, thread)
            return intent
        }
    }
}
