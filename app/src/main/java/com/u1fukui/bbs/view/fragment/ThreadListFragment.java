package com.u1fukui.bbs.view.fragment;

import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.R;
import com.u1fukui.bbs.databinding.FragmentThreadListBinding;
import com.u1fukui.bbs.databinding.ViewThreadCellBinding;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.view.customview.BindingHolder;
import com.u1fukui.bbs.view.customview.ObservableListRecyclerAdapter;
import com.u1fukui.bbs.viewmodel.ThreadListViewModel;
import com.u1fukui.bbs.viewmodel.ThreadViewModel;

public class ThreadListFragment extends Fragment {

    public static final String TAG = ThreadListFragment.class.getSimpleName();

    private static final String ARG_CATEGORY = "arg.categroy";

    private FragmentThreadListBinding binding;

    private ThreadListViewModel viewModel;

    private Adapter adapter;

    public static ThreadListFragment newInstance(Category category) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);

        ThreadListFragment instance = new ThreadListFragment();
        instance.setArguments(args);
        return instance;
    }

    public ThreadListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ThreadListViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentThreadListBinding.inflate(inflater, container, false);
        initViews();

        binding.setViewModel(viewModel);
        viewModel.start();

        return binding.getRoot();
    }

    private void initViews() {
        adapter = new Adapter(viewModel.getThreadViewModelList());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }

    private static class Adapter extends ObservableListRecyclerAdapter<ThreadViewModel, BindingHolder<ViewThreadCellBinding>> {

        public Adapter(@NonNull ObservableList<ThreadViewModel> list) {
            super(list);
        }

        @Override
        public BindingHolder<ViewThreadCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(parent.getContext(), parent, R.layout.view_thread_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewThreadCellBinding> holder, int position) {
            ThreadViewModel viewModel = getItem(position);
            ViewThreadCellBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }
    }
}
