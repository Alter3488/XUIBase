package com.lzz.lzzlibrary.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe: 基础ViewHolder
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 14:58
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private int layoutId;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(View itemView, int layoutId) {
        this(itemView);
        this.layoutId = layoutId;
    }

    public <T extends View> T findViewById(int id) {
        return itemView.findViewById(id);
    }

    public int getLayoutId() {
        return layoutId;
    }

}