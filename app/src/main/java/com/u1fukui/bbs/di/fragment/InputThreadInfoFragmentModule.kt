package com.u1fukui.bbs.di.fragment

import com.u1fukui.bbs.di.scope.FragmentScope
import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.ui.creation.thread.InputThreadInfoFragment

import dagger.Module
import dagger.Provides

@Module
class InputThreadInfoFragmentModule {

    @FragmentScope
    @Provides
    fun provideCategory(fragment: InputThreadInfoFragment): Category {
        return fragment.category
    }
}
