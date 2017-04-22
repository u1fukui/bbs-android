package com.u1fukui.bbs.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.ActivityCreateThreadBinding;

public class CreateThreadActivity extends AppCompatActivity {

    ActivityCreateThreadBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_thread);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.create_thread_title);
    }
}
