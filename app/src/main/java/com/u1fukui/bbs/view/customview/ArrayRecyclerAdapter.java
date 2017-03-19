package com.u1fukui.bbs.view.customview;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected final List<T> list;

    public ArrayRecyclerAdapter() {
        this(new ArrayList<T>());
    }

    public ArrayRecyclerAdapter(@NonNull List<T> list) {
        this.list = list;
    }

    @UiThread
    public void reset(Collection<T> items) {
        clear();
        addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
    }

    public void addAll(Collection<T> items) {
        list.addAll(items);
    }

    public T getItem(int position) {
        return list.get(position);
    }

    public void addItem(T item) {
        list.add(item);
    }

    public void addAll(int position, Collection<T> items) {
        list.addAll(position, items);
    }

    @UiThread
    public void addAllWithNotify(Collection<T> items) {
        int position = getItemCount();
        addAll(items);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
