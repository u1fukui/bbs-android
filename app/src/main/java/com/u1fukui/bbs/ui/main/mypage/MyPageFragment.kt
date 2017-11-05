package com.u1fukui.bbs.ui.main.mypage

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.u1fukui.bbs.databinding.FragmentMypageBinding

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMypageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        initViews()

        return binding.root
    }

    private fun initViews() {
        binding.apply {
            viewPager.adapter = MyPagePagerAdapter(childFragmentManager)
            tabLayout.tabMode = TabLayout.MODE_FIXED
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    private class MyPagePagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        internal enum class Tab {
            FAVORITE {
                //TODO: Apply resource string
                override val title = "Favorite"

                override fun createInsatance() = FavoriteThreadListFragment.newInstance()
            },
            HISTORY {
                //TODO: Apply resource string
                override val title = "History"

                override fun createInsatance() = HistoryThreadListFragment.newInstance()
            };

            internal abstract val title: String

            internal abstract fun createInsatance(): Fragment
        }

        override fun getItem(position: Int) = Tab.values()[position].createInsatance()

        override fun getCount() = Tab.values().size

        override fun getPageTitle(position: Int) = Tab.values()[position].title
    }

    companion object {

        @JvmStatic
        val TAG = MyPageFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() =  MyPageFragment()
    }
}
