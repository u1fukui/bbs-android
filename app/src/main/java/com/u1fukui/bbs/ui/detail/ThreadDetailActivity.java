package com.u1fukui.bbs.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.customview.BindingHolder;
import com.u1fukui.bbs.customview.ObservableListRecyclerAdapter;
import com.u1fukui.bbs.databinding.ActivityThreadDetailBinding;
import com.u1fukui.bbs.databinding.ViewCommentCellBinding;
import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.ui.BaseActivity;

import javax.inject.Inject;

public class ThreadDetailActivity extends BaseActivity {

    public static final String TAG = ThreadDetailActivity.class.getSimpleName();

    private static final String EXTRA_THREAD = "extra.thread";

    @Inject
    ThreadDetailNavigator navigator;

    @Inject
    ThreadRepository repository;

    private ActivityThreadDetailBinding binding;

    private Adapter adapter;

    private ThreadDetailViewModel viewModel;

    public static Intent createIntent(Context context, BbsThread thread) {
        Intent intent = new Intent(context, ThreadDetailActivity.class);
        intent.putExtra(EXTRA_THREAD, thread);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_thread_detail);

        BbsThread thread = (BbsThread) getIntent().getSerializableExtra(EXTRA_THREAD);
        viewModel = new ThreadDetailViewModel(thread, repository, navigator);
        binding.setViewModel(viewModel);
        initToolbar(binding.toolbar, true);

        initViews();
        viewModel.start();
    }

    private void initViews() {
        adapter = new Adapter(viewModel.getCommentViewModelList());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void onDestroy() {
        viewModel.destroy();
        binding.unbind();
        super.onDestroy();
    }

    private static class Adapter extends ObservableListRecyclerAdapter<CommentViewModel, BindingHolder<ViewCommentCellBinding>> {

        public Adapter(@NonNull ObservableList<CommentViewModel> list) {
            super(list);
        }

        @Override
        public BindingHolder<ViewCommentCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(parent.getContext(), parent, R.layout.view_comment_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewCommentCellBinding> holder, int position) {
            CommentViewModel viewModel = getItem(position);
            ViewCommentCellBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }
    }
}
