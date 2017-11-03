package com.u1fukui.bbs.ui.main

import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.u1fukui.bbs.R
import com.u1fukui.bbs.customview.BindingHolder
import com.u1fukui.bbs.customview.ObservableListRecyclerAdapter
import com.u1fukui.bbs.customview.RecyclerViewScrolledEndSubject
import com.u1fukui.bbs.databinding.FragmentThreadListBinding
import com.u1fukui.bbs.databinding.ViewThreadCellBinding
import com.u1fukui.bbs.repository.ThreadListRepository
import io.reactivex.disposables.Disposables

abstract class BaseThreadListFragment : Fragment() {

    private var binding: FragmentThreadListBinding? = null

    private var viewModel: ThreadListViewModel? = null

    private var adapter: Adapter? = null

    private var scrollEndSubject: RecyclerViewScrolledEndSubject? = null

    private var recyclerViewScrollEventDisposable = Disposables.empty()

    protected abstract val repository: ThreadListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigator = ThreadListNavigator(activity)
        viewModel = ThreadListViewModel(repository, navigator)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentThreadListBinding.inflate(inflater, container, false)
        initViews()
        initScrollEventListener()

        binding!!.viewModel = viewModel
        viewModel!!.start()

        return binding!!.root
    }

    private fun initViews() {
        adapter = Adapter(viewModel!!.threadViewModelList)
        binding!!.recyclerView.adapter = adapter
        binding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun initScrollEventListener() {
        scrollEndSubject = RecyclerViewScrolledEndSubject(binding!!.recyclerView)
    }

    override fun onResume() {
        super.onResume()
        startListenScrollEvent()
    }

    override fun onPause() {
        stopListenScrollEvent()
        super.onPause()
    }

    override fun onDestroyView() {
        scrollEndSubject!!.shutdown()
        viewModel!!.destroy()
        binding!!.unbind()
        super.onDestroyView()
    }

    private fun startListenScrollEvent() {
        recyclerViewScrollEventDisposable.dispose()
        recyclerViewScrollEventDisposable = scrollEndSubject!!.connect().subscribe({ o -> viewModel!!.loadNextPage() }) { throwable -> stopListenScrollEvent() }
    }

    private fun stopListenScrollEvent() {
        recyclerViewScrollEventDisposable.dispose()
    }

    private class Adapter(list: ObservableList<ThreadViewModel>) : ObservableListRecyclerAdapter<ThreadViewModel, BindingHolder<ViewThreadCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<ViewThreadCellBinding> {
            return BindingHolder(parent.context, parent, R.layout.view_thread_cell)
        }

        override fun onBindViewHolder(holder: BindingHolder<ViewThreadCellBinding>, position: Int) {
            val viewModel = getItem(position)
            val itemBinding = holder.binding
            itemBinding.viewModel = viewModel
            itemBinding.executePendingBindings()
        }
    }
}
