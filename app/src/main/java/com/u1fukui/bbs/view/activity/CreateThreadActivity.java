package com.u1fukui.bbs.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.ActivityCreateThreadBinding;
import com.u1fukui.bbs.repository.CategoryListRepository;
import com.u1fukui.bbs.viewmodel.CreateThreadViewModel;

public class CreateThreadActivity extends BaseActivity {

    private ActivityCreateThreadBinding binding;

    private CreateThreadViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CreateThreadViewModel(new CategoryListRepository());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_thread);
        binding.setViewModel(viewModel);

        initToolbar(binding.toolbar, true);
        viewModel.start();
    }
}
