package com.u1fukui.bbs.ui.creation.thread


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.u1fukui.bbs.databinding.FragmentInputThreadInfoBinding
import com.u1fukui.bbs.model.Category
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InputThreadInfoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: InputThreadInfoViewModel

    internal val category by lazy {
        arguments.getSerializable(ARG_CATEGORY) as Category
    }

    private lateinit var binding: FragmentInputThreadInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInputThreadInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        initViews()

        return binding.root
    }

    private fun initViews() {
        binding.editTextTitle.requestFocus()
    }

    companion object {

        private val ARG_CATEGORY = "arg.category"

        fun newInstance(category: Category) = InputThreadInfoFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CATEGORY, category)
            }
        }
    }
}
