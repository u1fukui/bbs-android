package com.u1fukui.bbs.ui.creation.thread

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.u1fukui.bbs.R
import com.u1fukui.bbs.customview.BindingHolder
import com.u1fukui.bbs.customview.ObservableListRecyclerAdapter
import com.u1fukui.bbs.databinding.FragmentSelectCategoryBinding
import com.u1fukui.bbs.databinding.ViewCategoryCellBinding
import com.u1fukui.bbs.repository.CategoryListRepository
import dagger.android.support.DaggerFragment

class SelectCategoryFragment : DaggerFragment() {

    private lateinit var binding: FragmentSelectCategoryBinding

    private val viewModel: SelectCategoryViewModel by lazy {
        ViewModelProviders
            .of(
                this, SelectCategoryViewModel.Factory(
                    CategoryListRepository(),
                    CreateThreadNavigator(activity as CreateThreadActivity)
                )
            )
            .get(SelectCategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        initViews()

        viewModel.start()

        return binding.root
    }

    private fun initViews() {
        binding.recyclerView.apply {
            adapter = Adapter(viewModel.categoryList)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    private class Adapter(list: ObservableList<CategoryBindingModel>) :
        ObservableListRecyclerAdapter<CategoryBindingModel, BindingHolder<ViewCategoryCellBinding>>(
            list
        ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BindingHolder<ViewCategoryCellBinding>(
                parent.context,
                parent,
                R.layout.view_category_cell
            )

        override fun onBindViewHolder(
            holder: BindingHolder<ViewCategoryCellBinding>,
            position: Int
        ) {
            holder.binding?.apply {
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
