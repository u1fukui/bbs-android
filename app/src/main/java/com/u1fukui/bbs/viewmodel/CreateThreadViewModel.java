package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryListRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateThreadViewModel implements ViewModel {

    public final ObservableField<List<String>> categoryNameList = new ObservableField<>();

    public final ObservableInt contentVisibility = new ObservableInt(View.GONE);

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    public String title;

    public String description;

    public int categoryIndex;

    private CategoryListRepository repository;

    public CreateThreadViewModel(CategoryListRepository repository) {
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
                        List<String> nameList = new ArrayList<>();
                        for (Category category : categoryList) {
                            nameList.add(category.name);
                        }
                        categoryNameList.set(nameList);
                        loadingVisibility.set(View.GONE);
                        contentVisibility.set(View.VISIBLE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //TODO: 実装
                        loadingVisibility.set(View.GONE);
                    }
                });
    }

    public void onClickPostButton(View view) {
        //TOOD: 実装
        Log.d("TAG10", "click: category=" + categoryIndex + ", title=" + title + ", description=" + description);
    }

    @Override
    public void destroy() {

    }
}
