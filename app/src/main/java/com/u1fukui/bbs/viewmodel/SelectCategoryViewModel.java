package com.u1fukui.bbs.viewmodel;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryListRepository;
import com.u1fukui.bbs.view.customview.ErrorView;
import com.u1fukui.bbs.view.helper.LoadingManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class SelectCategoryViewModel implements ViewModel, ErrorView.ErrorViewListener {

    public final LoadingManager loadingManager = new LoadingManager();

    @Getter
    private final ObservableList<CategoryViewModel> categoryList = new ObservableArrayList<>();

    private final CategoryListRepository repository;

    public SelectCategoryViewModel(CategoryListRepository repository) {
        this.repository = repository;
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
                            viewModelList.add(new CategoryViewModel(category));
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
}
