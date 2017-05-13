package com.u1fukui.bbs.view.activity;


import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.ActivityNotificationListBinding;
import com.u1fukui.bbs.databinding.ViewNotificationCellBinding;
import com.u1fukui.bbs.view.customview.BindingHolder;
import com.u1fukui.bbs.view.customview.ObservableListRecyclerAdapter;
import com.u1fukui.bbs.viewmodel.NotificationListViewModel;
import com.u1fukui.bbs.viewmodel.NotificationViewModel;

import javax.inject.Inject;

public class NotificationListActivity extends BaseActivity {

    @Inject
    NotificationListViewModel viewModel;

    private ActivityNotificationListBinding binding;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_list);
        binding.setViewModel(viewModel);

        initToolbar(binding.toolbar, true);
        initViews();

        viewModel.start();
    }

    private void initViews() {
        adapter = new Adapter(viewModel.getNotificationViewModelList());
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

    private static class Adapter extends ObservableListRecyclerAdapter<NotificationViewModel, BindingHolder<ViewNotificationCellBinding>> {

        public Adapter(@NonNull ObservableList<NotificationViewModel> list) {
            super(list);
        }

        @Override
        public BindingHolder<ViewNotificationCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(parent.getContext(), parent, R.layout.view_notification_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewNotificationCellBinding> holder, int position) {
            NotificationViewModel viewModel = getItem(position);
            ViewNotificationCellBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }
    }
}
