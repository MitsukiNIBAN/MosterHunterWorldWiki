package com.mitsuki.mosterhunterworldwiki.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<B, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_EMPTY = Integer.MIN_VALUE;

    protected List<B> mData;
    protected Context mContext;
    private int emptyLayout;
    private boolean useEmpty = false;

    public BaseAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
        emptyLayout = -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (getItemViewType(i) == VIEW_TYPE_EMPTY && emptyLayout != -1 && useEmpty) {
            return onCreateEmptyViewHolder(viewGroup, i);
        }
        return onMyCreateViewHolder(viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder t, int i) {
        if (getItemViewType(i) == VIEW_TYPE_EMPTY) {
            onBindEmptyViewHolder(t, i);
        } else {
            onMyBindViewHolder((T) t, i);
        }
    }

    public abstract T onMyCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    public abstract void onMyBindViewHolder(@NonNull T t, int i);

    public RecyclerView.ViewHolder onCreateEmptyViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View emptyView = LayoutInflater.from(mContext).inflate(emptyLayout, viewGroup, false);
        return new RecyclerView.ViewHolder(emptyView) {
        };
    }

    public void onBindEmptyViewHolder(@NonNull  RecyclerView.ViewHolder t, int i) {

    }


    @Override
    public int getItemCount() {
        if (mData.size() <= 0 && useEmpty && emptyLayout != -1)
            return 1;
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.size() <= 0 && useEmpty && emptyLayout != -1)
            return VIEW_TYPE_EMPTY;
        return super.getItemViewType(position);
    }

    public void setEmptyLayout(int emptyLayout) {
        this.emptyLayout = emptyLayout;
    }

    public void addAll(List<B> data) {
        mData.clear();
        mData.addAll(data);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public B getItem(int position) {
        return mData.get(position);
    }

    public void setUseEmpty(boolean useEmpty) {
        this.useEmpty = useEmpty;
    }
}
