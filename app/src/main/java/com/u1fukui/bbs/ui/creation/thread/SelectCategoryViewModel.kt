package com.u1fukui.bbs.ui.creation.thread


import android.databinding.ObservableArrayList
import android.databinding.ObservableList

import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.repository.CategoryListRepository
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.ui.ViewModel

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SelectCategoryViewModel @Inject
constructor(private val repository: CategoryListRepository, private val navigator: CreateThreadNavigator) : ViewModel, ErrorView.ErrorViewListener {

    val loadingManager = LoadingManager()

    //TODO: 整理する
    internal val categoryList: ObservableList<CategoryViewModel> = ObservableArrayList()

    fun start() {
        if (categoryList.isEmpty()) {
            fetchCategoryList()
        }
    }

    private fun fetchCategoryList() {
        if (loadingManager.isLoading) {
            return
        }
        loadingManager.startLoading()

        repository.fetchCategoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<Category>> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        // nop
                    }

                    override fun onSuccess(@NonNull categoryList: List<Category>) {
                        val viewModelList = ArrayList<CategoryViewModel>()
                        for (category in categoryList) {
                            viewModelList.add(CategoryViewModel(category, navigator))
                        }

                        this@SelectCategoryViewModel.categoryList.clear()
                        this@SelectCategoryViewModel.categoryList.addAll(viewModelList)
                        loadingManager.showContentView()
                    }

                    override fun onError(@NonNull e: Throwable) {
                        loadingManager.showErrorView(e)
                    }
                })
    }

    override fun destroy() {

    }

    override fun onClickReloadButton() {
        fetchCategoryList()
    }
}
