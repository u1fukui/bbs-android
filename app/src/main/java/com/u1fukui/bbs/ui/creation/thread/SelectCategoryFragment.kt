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

    private var binding: FragmentSelectCategoryBinding? = null

    @Inject
    lateinit var viewModel: SelectCategoryViewModel

    private var adapter: Adapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        binding!!.viewModel = viewModel
        initViews()

        viewModel.start()

        return binding!!.root
    }

    private fun initViews() {
        adapter = Adapter(viewModel.categoryList)
        binding!!.recyclerView.adapter = adapter
        binding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private class Adapter(list: ObservableList<CategoryViewModel>) : ObservableListRecyclerAdapter<CategoryViewModel, BindingHolder<ViewCategoryCellBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<ViewCategoryCellBinding> {
            return BindingHolder(parent.context, parent, R.layout.view_category_cell)
        }

        override fun onBindViewHolder(holder: BindingHolder<ViewCategoryCellBinding>, position: Int) {
            val viewModel = getItem(position)
            val itemBinding = holder.binding
            itemBinding.viewModel = viewModel
            itemBinding.executePendingBindings()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): SelectCategoryFragment {
            return SelectCategoryFragment()
        }
    }
}
