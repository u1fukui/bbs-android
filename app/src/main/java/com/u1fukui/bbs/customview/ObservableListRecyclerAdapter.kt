package com.u1fukui.bbs.customview

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

abstract class ObservableListRecyclerAdapter<T, VH : RecyclerView.ViewHolder>(list: ObservableList<T>) :
    ArrayRecyclerAdapter<T, VH>(list) {

    init {
        list.addOnListChangedCallback(object :
            ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(list: ObservableList<T>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(list: ObservableList<T>, i: Int, i1: Int) {
                notifyItemRangeChanged(i, i1)
            }

            override fun onItemRangeInserted(list: ObservableList<T>, i: Int, i1: Int) {
                notifyItemRangeInserted(i, i1)
            }

            override fun onItemRangeMoved(list: ObservableList<T>, i: Int, i1: Int, i2: Int) {
                notifyItemMoved(i, i1)
            }

            override fun onItemRangeRemoved(list: ObservableList<T>, i: Int, i1: Int) {
                notifyItemRangeRemoved(i, i1)
            }
        })
    }
}
