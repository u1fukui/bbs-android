package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryListRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class HomeViewModel implements ViewModel{

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    @Getter
    private final ObservableList<Category> categoryList = new ObservableArrayList<>();

    private final CategoryListRepository repository;

    public HomeViewModel(CategoryListRepository repository) {
        this.repository = repository;
    }

    public void start() {
        fetchCategoryList();
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

                        loadingVisibility.set(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //TODO: エラー表示対応
                        loadingVisibility.set(View.GONE);
                    }
                });
    }

    @Override
    public void destroy() {

    }
}
