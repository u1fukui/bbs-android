package com.u1fukui.bbs.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import android.view.View
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.repository.CategoryListRepository
import com.u1fukui.bbs.ui.main.MainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
        private val repository: CategoryListRepository,
        private val navigator: MainNavigator
) : ViewModel(), ErrorView.ErrorViewListener {

    //region DataBinding
    val loadingManager = LoadingManager()
    //endregion

    //TODO: 整理する
    internal val categoryList: ObservableList<Category> = ObservableArrayList()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

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
                .subscribeBy(
                        onSuccess = {
                            this@HomeViewModel.categoryList.clear()
                            this@HomeViewModel.categoryList.addAll(it)
                            loadingManager.showContentView()
                        },
                        onError = {
                            loadingManager.showErrorView(it)
                        }
                )
                .addTo(compositeDisposable)
    }

    fun onClickFloatingActionButton(view: View) {
        navigator.navigateToCreateThreadPage()
    }

    override fun onClickReloadButton() {
        fetchCategoryList()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory(
            private val repository: CategoryListRepository,
            private val navigator: MainNavigator
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeViewModel(
                repository,
                navigator
        ) as T
    }
}
