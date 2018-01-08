package com.u1fukui.bbs.ui.main.home

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.view.View
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.repository.CategoryListRepository
import com.u1fukui.bbs.ui.BindingModel
import com.u1fukui.bbs.ui.main.MainNavigator
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeBindingModel @Inject
constructor(
        private val repository: CategoryListRepository,
        private val navigator: MainNavigator
) : BindingModel, ErrorView.ErrorViewListener {

    val loadingManager = LoadingManager()

    //TODO: 整理する
    internal val categoryList: ObservableList<Category> = ObservableArrayList()

    fun onClickFloatingActionButton(view: View) {
        navigator.navigateToCreateThreadPage()
    }

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
                        this@HomeBindingModel.categoryList.clear()
                        this@HomeBindingModel.categoryList.addAll(categoryList)
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
