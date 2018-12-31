package com.u1fukui.bbs.paging.comment

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import com.u1fukui.bbs.R
import com.u1fukui.bbs.customview.BindingHolder
import com.u1fukui.bbs.databinding.ViewCommentCellBinding
import com.u1fukui.bbs.ui.detail.CommentBindingModel

class CommentPagedListAdapter() :
    PagedListAdapter<CommentBindingModel, BindingHolder<ViewCommentCellBinding>>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<ViewCommentCellBinding> {
        return BindingHolder(parent.context, parent, R.layout.view_comment_cell)
    }

    override fun onBindViewHolder(holder: BindingHolder<ViewCommentCellBinding>, position: Int) {
        holder.binding?.let {
            it.bindingModel = getItem(position)
            it.executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentBindingModel>() {
            override fun areItemsTheSame(
                oldItem: CommentBindingModel,
                newItem: CommentBindingModel
            ): Boolean {
                return oldItem.comment.id == newItem.comment.id
            }

            override fun areContentsTheSame(
                oldItem: CommentBindingModel,
                newItem: CommentBindingModel
            ): Boolean {
                return oldItem.comment == newItem.comment
            }
        }
    }
}