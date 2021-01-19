package com.lzz.lzzlibrary.adapter;

import android.view.View;

/**
 * Describe: item相关监听
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 15:00
 */
public interface ItemListener {

    interface OnItemClickListener<T> {
        /**
         * item点击监听
         *
         * @param view
         * @param item
         * @param position
         */
        void onItemClick(View view, T item, int position);
    }

    interface OnItemLongClickListener<T> {
        /**
         * item长按监听
         *
         * @param view
         * @param item
         * @param position
         */
        void onItemLongClick(View view, T item, int position);
    }

    interface OnItemViewClickListener<T> {
        /**
         * item中某个view点击监听
         *
         * @param view
         * @param item
         * @param position
         */
        void onItemViewClick(View view, T item, int position);
    }

}
