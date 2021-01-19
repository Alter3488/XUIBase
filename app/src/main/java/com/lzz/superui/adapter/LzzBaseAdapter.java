package com.lzz.superui.adapter;

import android.util.Log;

import java.util.List;

/**
 * Describe: 基准适配器
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 14:57
 */
public abstract class LzzBaseAdapter<T, V extends BaseViewHolder> extends DataAdapter<T, V> implements AdapterImpl<T, V> {

    public LzzBaseAdapter() {
        super();
    }

    public LzzBaseAdapter(List<T> beanList) {
        super(beanList);
    }

    /**
     * 获取当前加载的那个布局
     *
     * @param
     * @return
     */
    public int getLayoutId(BaseViewHolder baseViewHolder) {
        return baseViewHolder.getLayoutId();
    }

    /**
     * 共享Item。其实RecycledViewPool的内部维护了一个Map，
     * <p>
     * 里面以不同的viewType为Key存储了各自对应的ViewHolder集合。可以通过提供的方法来修改内部缓存的Viewholder
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int layout = getLayout(getDataList().get(position), position);
        Log.d(TAG, "getItemViewType: " + layout);
        return layout;
    }

    @Override
    public void onBindViewHolder(V viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder:position:" + position);
        if (getDataList() != null) {
            onBindViewHolder(viewHolder, getDataList().get(position), position);
            // 绑定监听事件
            onBindItemClickListener(viewHolder, position);
            // 自定义监听事件
            listener(viewHolder, getDataList().get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        if (getDataList() != null) {
            return getDataList().size();
        }
        return 0;
    }
}
