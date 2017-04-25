package com.u1fukui.bbs.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.ActivityCreateThreadBinding;
import com.u1fukui.bbs.view.fragment.SelectCategoryFragment;

public class CreateThreadActivity extends BaseActivity {

    private ActivityCreateThreadBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_thread);

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
