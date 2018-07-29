package com.u1fukui.bbs.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.u1fukui.bbs.databinding.FragmentThreadListBinding
import com.u1fukui.bbs.paging.thread.ThreadPagedListAdapter
import com.u1fukui.bbs.repository.thread_list.ThreadListRepository
import dagger.android.support.DaggerFragment

abstract class BaseThreadListFragment : DaggerFragment() {

    private val viewModel: ThreadListViewModel by lazy {
        ViewModelProviders
            .of(
                this,
                ThreadListViewModel.Factory(
                    this,
                    getRepository(),
                    ThreadListNavigator(activity!!)
                )
            )
            .get(ThreadListViewModel::class.java)
    }

    private val adapter = ThreadPagedListAdapter()
    private lateinit var binding: FragmentThreadListBinding

    protected abstract fun getRepository(): ThreadListRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThreadListBinding.inflate(inflater, container, false)
        initViews()

        viewModel.threadBindingModelList.observe(this, Observer {
            adapter.submitList(it)
        })

        binding.viewModel = viewModel
        return binding.root
    }

    private fun initViews() {
        binding.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context)
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
