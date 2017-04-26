package com.u1fukui.bbs.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.ActivityCreateThreadBinding;
import com.u1fukui.bbs.view.fragment.SelectCategoryFragment;
import com.u1fukui.bbs.view.helper.CreateThreadNavigator;

import lombok.Getter;

public class CreateThreadActivity extends BaseActivity {

    private ActivityCreateThreadBinding binding;

    @Getter
    private CreateThreadNavigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_thread);
        navigator = new CreateThreadNavigator(this, R.id.fragment_container);

        initToolbar(binding.toolbar, true);
        initViews(savedInstanceState);
    }

    private void initViews(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, SelectCategoryFragment.newInstance())
                .commit();
    }
}
