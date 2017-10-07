package com.u1fukui.bbs.ui.main;

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
import com.u1fukui.bbs.customview.BindingHolder;
import com.u1fukui.bbs.customview.ObservableListRecyclerAdapter;
import com.u1fukui.bbs.customview.RecyclerViewScrolledEndSubject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;

public abstract class BaseThreadListFragment extends Fragment {

    private FragmentThreadListBinding binding;

    private ThreadListViewModel viewModel;

    private Adapter adapter;

    private RecyclerViewScrolledEndSubject scrollEndSubject;

    private Disposable recyclerViewScrollEventDisposable = Disposables.empty();

    abstract protected ThreadListRepository getRepository();

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
        initScrollEventListener();

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

    private void initScrollEventListener() {
        scrollEndSubject = new RecyclerViewScrolledEndSubject(binding.recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        startListenScrollEvent();
    }

    @Override
    public void onPause() {
        stopListenScrollEvent();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        scrollEndSubject.shutdown();
        viewModel.destroy();
        binding.unbind();
        super.onDestroyView();
    }

    private void startListenScrollEvent() {
        recyclerViewScrollEventDisposable.dispose();
        recyclerViewScrollEventDisposable = scrollEndSubject.connect().subscribe(new Consumer() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                viewModel.loadNextPage();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                stopListenScrollEvent();
            }
        });
    }

    private void stopListenScrollEvent() {
        recyclerViewScrollEventDisposable.dispose();
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
