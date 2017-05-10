package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryListRepository;
import com.u1fukui.bbs.view.customview.ErrorView;
import com.u1fukui.bbs.view.helper.LoadingManager;
import com.u1fukui.bbs.view.helper.MainNavigator;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class HomeViewModel implements ViewModel, ErrorView.ErrorViewListener {

    public final LoadingManager loadingManager = new LoadingManager();

    @Getter
    private final ObservableList<Category> categoryList = new ObservableArrayList<>();

    private final CategoryListRepository repository;

    private final MainNavigator navigator;

    @Inject
    public HomeViewModel(CategoryListRepository repository, MainNavigator navigator) {
        this.repository = repository;
        this.navigator = navigator;
    }

    public void onClickFloatingActionButton(View view) {
        navigator.navigateToCreateThreadPage();
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
                        HomeViewModel.this.categoryList.clear();
                        HomeViewModel.this.categoryList.addAll(categoryList);
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
