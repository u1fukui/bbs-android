package com.u1fukui.bbs.view.activity;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.ActivityCreateCommentBinding;
import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.User;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.view.helper.CreateCommentNavigator;
import com.u1fukui.bbs.viewmodel.CreateCommentViewModel;

public class CreateCommentActivity extends BaseActivity {

    private static final String EXTRA_THREAD = "extra.thread";

    private ActivityCreateCommentBinding binding;

    private CreateCommentViewModel viewModel;

    public static Intent createIntent(Context context, BbsThread thread) {
        Intent intent = new Intent(context, CreateCommentActivity.class);
        intent.putExtra(EXTRA_THREAD, thread);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = createViewModel();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_comment);
        binding.setViewModel(viewModel);
        initToolbar(binding.toolbar, true);
    }

    private CreateCommentViewModel createViewModel() {
        BbsThread thread = (BbsThread) getIntent().getSerializableExtra(EXTRA_THREAD);
        User user = new User(1, "たろう");
        ThreadRepository repository = new ThreadRepository();
        CreateCommentNavigator navigator = new CreateCommentNavigator(this);
        return new CreateCommentViewModel(thread, user, repository, navigator);
    }

    @Override
    protected void onDestroy() {
        binding.unbind();
        super.onDestroy();
    }
}
