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
import com.u1fukui.bbs.repository.ThreadListRepository;
import com.u1fukui.bbs.view.customview.BindingHolder;
import com.u1fukui.bbs.view.customview.ObservableListRecyclerAdapter;
import com.u1fukui.bbs.view.helper.ThreadListNavigator;
import com.u1fukui.bbs.viewmodel.ThreadListViewModel;
import com.u1fukui.bbs.viewmodel.ThreadViewModel;

public abstract class BaseThreadListFragment extends Fragment {

    private FragmentThreadListBinding binding;

    private ThreadListViewModel viewModel;

    private Adapter adapter;

    abstract ThreadListRepository getRepository();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThreadListNavigator navigator = new ThreadListNavigator(getActivity());
        viewModel = new ThreadListViewModel(getRepository(), navigator);
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
        viewModel.destroy();
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
