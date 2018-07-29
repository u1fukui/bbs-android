package com.u1fukui.bbs.paging

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.u1fukui.bbs.R
import com.u1fukui.bbs.customview.BindingHolder
import com.u1fukui.bbs.databinding.ViewThreadCellBinding
import com.u1fukui.bbs.ui.main.ThreadBindingModel

class ThreadPagedListAdapter() :
    PagedListAdapter<ThreadBindingModel, BindingHolder<ViewThreadCellBinding>>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<ViewThreadCellBinding> {
        return BindingHolder(parent.context, parent, R.layout.view_thread_cell)
    }

    override fun onBindViewHolder(holder: BindingHolder<ViewThreadCellBinding>, position: Int) {
        holder.binding?.let {
            it.bindingModel = getItem(position)
            it.executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ThreadBindingModel>() {
            override fun areItemsTheSame(
                oldItem: ThreadBindingModel,
                newItem: ThreadBindingModel
            ): Boolean {
                return oldItem.bbsThread.id == newItem.bbsThread.id
            }

            override fun areContentsTheSame(
                oldItem: ThreadBindingModel,
                newItem: ThreadBindingModel
            ): Boolean {
                return oldItem.bbsThread == newItem.bbsThread
            }
        }
    }
}