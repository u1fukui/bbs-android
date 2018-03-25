package com.u1fukui.bbs.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.ObservableList
import android.os.Bundle
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
import com.u1fukui.bbs.repository.thread_list.ThreadListRepository
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposables


abstract class BaseThreadListFragment : DaggerFragment() {

    private val viewModel: ThreadListViewModel by lazy {
        ViewModelProviders
                .of(this, ThreadListViewModel.Factory(getRepository(), ThreadListNavigator(activity!!)))
                .get(ThreadListViewModel::class.java)
    }

    private lateinit var binding: FragmentThreadListBinding
    private lateinit var scrollEndSubject: RecyclerViewScrolledEndSubject
    private var recyclerViewScrollEventDisposable = Disposables.empty()

    protected abstract fun getRepository(): ThreadListRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentThreadListBinding.inflate(inflater, container, false)
        initViews()
        initScrollEventListener()

        binding.viewModel = viewModel
        viewModel.start()

        return binding.root
    }

    private fun initViews() {
        binding.recyclerView.apply {
            adapter = Adapter(viewModel.threadBindingModelList)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initScrollEventListener() {
        scrollEndSubject = RecyclerViewScrolledEndSubject(binding.recyclerView)
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
        scrollEndSubject.shutdown()
        binding.unbind()
        super.onDestroyView()
    }

    private fun startListenScrollEvent() {
        recyclerViewScrollEventDisposable.dispose()
        recyclerViewScrollEventDisposable = scrollEndSubject.connect().subscribe({ viewModel.loadNextPage() }) { stopListenScrollEvent() }
    }

    private fun stopListenScrollEvent() {
        recyclerViewScrollEventDisposable.dispose()
    }

    private class Adapter(list: ObservableList<ThreadBindingModel>) : ObservableListRecyclerAdapter<ThreadBindingModel, BindingHolder<ViewThreadCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder<ViewThreadCellBinding>(parent.context, parent, R.layout.view_thread_cell)

        override fun onBindViewHolder(holder: BindingHolder<ViewThreadCellBinding>, position: Int) {
            holder.binding?.apply {
                bindingModel = getItem(position)
                executePendingBindings()
            }
        }
    }
}
