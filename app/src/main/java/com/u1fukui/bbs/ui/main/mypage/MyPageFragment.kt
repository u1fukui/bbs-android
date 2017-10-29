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

    private var binding: FragmentMypageBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        initViews()

        return binding!!.root
    }

    private fun initViews() {
        binding!!.tabLayout.tabMode = TabLayout.MODE_FIXED
        binding!!.viewPager.adapter = MyPagePagerAdapter(childFragmentManager)
        binding!!.tabLayout.setupWithViewPager(binding!!.viewPager)
    }

    override fun onDestroyView() {
        binding!!.unbind()
        super.onDestroyView()
    }

    private class MyPagePagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        internal enum class Tab {
            FAVORITE {

                override//TODO: Apply resource string
                val title: String
                    get() = "Favorite"

                override fun createInsatance(): Fragment {
                    return FavoriteThreadListFragment.newInstance()
                }
            },
            HISTORY {

                override//TODO: Apply resource string
                val title: String
                    get() = "History"

                override fun createInsatance(): Fragment {
                    return HistoryThreadListFragment.newInstance()
                }
            };

            internal abstract val title: String

            internal abstract fun createInsatance(): Fragment
        }

        override fun getItem(position: Int): Fragment {
            val tab = Tab.values()[position]
            return tab.createInsatance()
        }

        override fun getCount(): Int {
            return Tab.values().size
        }

        override fun getPageTitle(position: Int): CharSequence {
            val tab = Tab.values()[position]
            return tab.title
        }
    }

    companion object {

        val TAG = MyPageFragment::class.java.simpleName

        fun newInstance(): MyPageFragment {
            return MyPageFragment()
        }
    }
}
