package com.u1fukui.bbs.customview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class RecyclerViewScrolledEndSubject(private var recyclerView: RecyclerView?) {

    private val subject = PublishSubject.create<Any>()

    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            val manager = recyclerView!!.layoutManager as LinearLayoutManager

            val visibleCount = recyclerView.childCount
            val totalCount = manager.itemCount
            val firstPos = manager.findFirstVisibleItemPosition()

            if (totalCount <= firstPos + visibleCount + THRESHOLD_COUNT) {
                subject.onNext(Irrelevant.INSTANCE)
            }
        }
    }

    internal enum class Irrelevant {
        INSTANCE
    }

    init {
        if (recyclerView?.layoutManager !is LinearLayoutManager) {
            throw IllegalArgumentException("RecyclerView needs to have LinearLayoutManager")
        }
        this.recyclerView?.addOnScrollListener(listener)
    }

    fun connect(): Subject<*> {
        return subject
    }

    fun shutdown() {
        recyclerView?.removeOnScrollListener(listener)
        recyclerView = null
    }

    companion object {

        private const val THRESHOLD_COUNT = 5
    }
}
