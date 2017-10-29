package com.u1fukui.bbs.ui.creation.thread;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryListRepository;
import com.u1fukui.bbs.customview.ErrorView;
import com.u1fukui.bbs.helper.LoadingManager;
import com.u1fukui.bbs.ui.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectCategoryViewModel implements ViewModel, ErrorView.ErrorViewListener {

    public final LoadingManager loadingManager = new LoadingManager();

    private final ObservableList<CategoryViewModel> categoryList = new ObservableArrayList<>();

    private final CategoryListRepository repository;

    private final CreateThreadNavigator navigator;

    @Inject
    public SelectCategoryViewModel(CategoryListRepository repository, CreateThreadNavigator navigator) {
        this.repository = repository;
        this.navigator = navigator;
    }

    public void start() {
        if (categoryList.isEmpty()) {
            fetchCategoryList();
        }
    }

    private void fetchCategoryList() {
        if (loadingManager.isLoading()) {
            return;
        }
        loadingManager.startLoading();

        repository.fetchCategoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Category>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // nop
                    }

                    @Override
                    public void onSuccess(@NonNull List<Category> categoryList) {
                        List<CategoryViewModel> viewModelList = new ArrayList<>();
                        for (Category category : categoryList) {
                            viewModelList.add(new CategoryViewModel(category, navigator));
                        }

                        SelectCategoryViewModel.this.categoryList.clear();
                        SelectCategoryViewModel.this.categoryList.addAll(viewModelList);
                        loadingManager.showContentView();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadingManager.showErrorView(e);
                    }
                });
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClickReloadButton() {
        fetchCategoryList();
    }

    //TODO: 整理する
    ObservableList<CategoryViewModel> getCategoryList() {
        return categoryList;
    }
}