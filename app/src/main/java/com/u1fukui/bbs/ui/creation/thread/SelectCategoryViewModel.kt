package com.u1fukui.bbs.ui.creation.thread


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.repository.CategoryListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SelectCategoryViewModel(
        private val repository: CategoryListRepository,
        private val navigator: CreateThreadNavigator
) : ViewModel(), ErrorView.ErrorViewListener {

    val loadingManager = LoadingManager()

    //TODO: 整理する
    internal val categoryList: ObservableList<CategoryBindingModel> = ObservableArrayList()

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
                        onSuccess = { response ->
                            val bindingModelList = response.map { CategoryBindingModel(it, navigator) }

                            this@SelectCategoryViewModel.categoryList.clear()
                            this@SelectCategoryViewModel.categoryList.addAll(bindingModelList)
                            loadingManager.showContentView()
                        },
                        onError = {
                            loadingManager.showErrorView(it)
                        }
                )
                .addTo(compositeDisposable)
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
            private val navigator: CreateThreadNavigator
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SelectCategoryViewModel(
                repository,
                navigator
        ) as T
    }
}
