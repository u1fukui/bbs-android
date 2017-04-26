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
import com.u1fukui.bbs.databinding.FragmentSelectCategoryBinding;
import com.u1fukui.bbs.databinding.ViewCategoryCellBinding;
import com.u1fukui.bbs.repository.CategoryListRepository;
import com.u1fukui.bbs.view.activity.CreateThreadActivity;
import com.u1fukui.bbs.view.customview.BindingHolder;
import com.u1fukui.bbs.view.customview.ObservableListRecyclerAdapter;
import com.u1fukui.bbs.view.helper.CreateThreadNavigator;
import com.u1fukui.bbs.viewmodel.CategoryViewModel;
import com.u1fukui.bbs.viewmodel.SelectCategoryViewModel;

public class SelectCategoryFragment extends Fragment {

    private FragmentSelectCategoryBinding binding;

    private SelectCategoryViewModel viewModel;

    private Adapter adapter;

    public static SelectCategoryFragment newInstance() {
        return new SelectCategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof CreateThreadActivity) {
            CreateThreadNavigator navigator = ((CreateThreadActivity) getActivity()).getNavigator();
            viewModel = new SelectCategoryViewModel(new CategoryListRepository(), navigator);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initViews();

        viewModel.start();

        return binding.getRoot();
    }

    private void initViews() {
        adapter = new Adapter(viewModel.getCategoryList());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private static class Adapter extends ObservableListRecyclerAdapter<CategoryViewModel, BindingHolder<ViewCategoryCellBinding>> {

        public Adapter(@NonNull ObservableList<CategoryViewModel> list) {
            super(list);
        }

        @Override
        public BindingHolder<ViewCategoryCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(parent.getContext(), parent, R.layout.view_category_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewCategoryCellBinding> holder, int position) {
            CategoryViewModel viewModel = getItem(position);
            ViewCategoryCellBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }
    }
}
