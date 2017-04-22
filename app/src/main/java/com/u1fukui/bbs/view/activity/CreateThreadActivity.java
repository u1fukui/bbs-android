package com.u1fukui.bbs.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.ActivityCreateThreadBinding;

public class CreateThreadActivity extends BaseActivity {

    ActivityCreateThreadBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_thread);

        initToolbar(binding.toolbar, true);
        setSupportActionBar(binding.toolbar);
    }
}
