package com.u1fukui.bbs.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.u1fukui.bbs.R
import com.u1fukui.bbs.databinding.ActivityMainBinding
import com.u1fukui.bbs.ui.BaseActivity
import com.u1fukui.bbs.ui.main.home.HomeFragment
import com.u1fukui.bbs.ui.main.mypage.MyPageFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    @Inject
    lateinit var navigator: MainNavigator

    private var homeFragment: Fragment? = null

    private var myPageFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(binding.toolbar, false)
        initViews()
        initFragments(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_notification, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_notification -> {
                navigator.navigateToNotificationPage()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    private fun initViews() {
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            supportActionBar!!.title = item.title
            item.isChecked = true
            when (item.itemId) {
                R.id.nav_home -> switchFragment(homeFragment, HomeFragment.TAG)
                R.id.nav_mypage -> switchFragment(myPageFragment, MyPageFragment.TAG)
            }
            false
        }
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        val manager = supportFragmentManager
        homeFragment = manager.findFragmentByTag(HomeFragment.TAG)?: HomeFragment.newInstance()
        myPageFragment = manager.findFragmentByTag(MyPageFragment.TAG)?: MyPageFragment.newInstance()
        if (savedInstanceState == null) {
            switchFragment(homeFragment, HomeFragment.TAG)
        }
    }

    private fun switchFragment(fragment: androidx.fragment.app.Fragment?, tag: String): Boolean {
        if (fragment == null || fragment.isAdded) {
            return false
        }

        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        val currentFragment = manager.findFragmentById(R.id.content_view)
        if (currentFragment != null) {
            ft.detach(currentFragment)
        }
        if (fragment.isDetached) {
            ft.attach(fragment)
        } else {
            ft.add(R.id.content_view, fragment, tag)
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitNow()

        return true
    }
}
