package com.lzz.superui.adapter;

import androidx.annotation.LayoutRes;

/**
 * Describe:
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 15:03
 */
public interface AdapterImpl<T, V extends BaseViewHolder> {

    /**
     * 加载哪个布局id
     *
     * @return
     */
    @LayoutRes
    int getLayout(T item, int position);

    /**
     * 绑定视图
     *
     * @param viewHolder
     * @param item
     * @param position
     */
    void onBindViewHolder(V viewHolder, T item, int position);

    /**
     * view监听写在这里面
     */
    void listener(V viewHolder, T item, int position);

}
