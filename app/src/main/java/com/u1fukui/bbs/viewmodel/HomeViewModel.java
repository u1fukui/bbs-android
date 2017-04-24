package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryListRepository;
import com.u1fukui.bbs.view.customview.ErrorView;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class HomeViewModel implements ViewModel, ErrorView.ErrorViewListener {

    public final ObservableInt contentVisibility = new ObservableInt(View.GONE);

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    public final ObservableInt errorViewVisibility = new ObservableInt(View.GONE);

    public final ObservableField<String> errorMessage = new ObservableField<>();

    @Getter
    private final ObservableList<Category> categoryList = new ObservableArrayList<>();

    private final CategoryListRepository repository;

    public HomeViewModel(CategoryListRepository repository) {
        this.repository = repository;
    }

    public void start() {
        if (categoryList.isEmpty()) {
            fetchCategoryList();
        }
    }

    private void fetchCategoryList() {
        if (loadingVisibility.get() == View.VISIBLE) {
            return;
        }
        loadingVisibility.set(View.VISIBLE);

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

                        errorViewVisibility.set(View.GONE);
                        loadingVisibility.set(View.GONE);
                        contentVisibility.set(View.VISIBLE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        contentVisibility.set(View.GONE);
                        loadingVisibility.set(View.GONE);

                        //TODO: エラー文言
                        errorMessage.set("エラーです");
                        errorViewVisibility.set(View.VISIBLE);
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
