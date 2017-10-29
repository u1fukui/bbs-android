package com.u1fukui.bbs.ui.main.home

import android.databinding.ObservableList
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.u1fukui.bbs.databinding.FragmentHomeBinding
import com.u1fukui.bbs.model.Category
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding!!.viewModel = viewModel
        initViews()

        viewModel.start()

        return binding!!.root
    }

    private fun initViews() {
        binding!!.viewPager.adapter = HomePagerAdapter(childFragmentManager, viewModel.categoryList)
        binding!!.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        binding!!.tabLayout.setupWithViewPager(binding!!.viewPager)
    }

    override fun onDestroyView() {
        binding!!.unbind()
        super.onDestroyView()
    }

    private class HomePagerAdapter(manager: FragmentManager, private val categoryList: ObservableList<Category>) : FragmentPagerAdapter(manager) {

        init {
            this.categoryList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Category>>() {
                override fun onChanged(sender: ObservableList<Category>) {
                    notifyDataSetChanged()
                }

                override fun onItemRangeChanged(sender: ObservableList<Category>, positionStart: Int, itemCount: Int) {
                    notifyDataSetChanged()
                }

                override fun onItemRangeInserted(sender: ObservableList<Category>, positionStart: Int, itemCount: Int) {
                    notifyDataSetChanged()
                }

                override fun onItemRangeMoved(sender: ObservableList<Category>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                    notifyDataSetChanged()
                }

                override fun onItemRangeRemoved(sender: ObservableList<Category>, positionStart: Int, itemCount: Int) {
                    notifyDataSetChanged()
                }
            })
        }

        override fun getItem(position: Int): Fragment {
            return CategoryThreadListFragment.newInstance(categoryList[position])
        }

        override fun getCount(): Int {
            return categoryList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return categoryList[position].name
        }
    }

    companion object {

        @JvmStatic
        val TAG = HomeFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
