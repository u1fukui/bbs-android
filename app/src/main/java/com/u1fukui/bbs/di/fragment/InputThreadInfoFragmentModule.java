package com.u1fukui.bbs.di.fragment;

import com.u1fukui.bbs.di.scope.FragmentScope;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.view.fragment.InputThreadInfoFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class InputThreadInfoFragmentModule {

    @FragmentScope
    @Provides
    public Category provideCategory(InputThreadInfoFragment fragment) {
        return fragment.getCategory();
    }
}
