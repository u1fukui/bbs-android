package com.u1fukui.bbs.ui.creation.thread


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
import com.u1fukui.bbs.databinding.FragmentSelectCategoryBinding
import com.u1fukui.bbs.databinding.ViewCategoryCellBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SelectCategoryFragment : DaggerFragment() {

    @Inject
    lateinit var bindingModel: SelectCategoryBindingModel

    private lateinit var binding: FragmentSelectCategoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        binding.bindingModel = bindingModel
        initViews()

        bindingModel.start()

        return binding!!.root
    }

    private fun initViews() {
        binding.recyclerView.apply {
            adapter = Adapter(bindingModel.categoryList)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private class Adapter(list: ObservableList<CategoryBindingModel>) : ObservableListRecyclerAdapter<CategoryBindingModel, BindingHolder<ViewCategoryCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder<ViewCategoryCellBinding>(parent.context, parent, R.layout.view_category_cell)

        override fun onBindViewHolder(holder: BindingHolder<ViewCategoryCellBinding>, position: Int) {
            holder.binding.apply {
                bindingModel = getItem(position)
                executePendingBindings()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SelectCategoryFragment()
    }
}
