package com.lzz.superui.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe: 包含基本数据操作的 Adapter
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 14:59
 */
public abstract class DataAdapter<T, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    public String TAG = getClass().getSimpleName();
    private final List<T> mList;
    private ItemListener.OnItemClickListener mOnItemClickListener;
    private ItemListener.OnItemLongClickListener mOnItemLongClickListener;
    private ItemListener.OnItemViewClickListener mOnItemViewClickListener;

    public DataAdapter() {
        this(new ArrayList<T>());
    }

    public DataAdapter(List<T> beanList) {
        this.mList = beanList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 替换所有数据（全部刷新）
     *
     * @param data
     */
    public void replaceAll(List<T> data) {
        mList.clear();
        addAllNotifyAll(0, data);
    }

    /**
     * 添加数据集到列表头部 (全部刷新)
     * 注：
     *
     * @param data
     */
    public void addItemToHeadNotifyAll(List<T> data) {
        addAllNotifyAll(0, data);
    }

    /**
     * 添加数据集到列表头部(全部刷新)
     * 注：
     * 1.此方法适用于  上拉加载历史聊天内容
     * 2.此方法 Adapter只会刷新添加的数据（效率优化，前面的数据没必要刷新）
     *
     * @param data
     * @return 是否添加成功
     */
    public void addItemToHeadNotifyAll(T data) {
        addAllNotifyAll(0, data);
    }

    /**
     * 添加数据集到列表头部(局部刷新)
     * 注：
     * 1.此方法适用于  上拉加载历史聊天内容
     * 2.此方法 Adapter只会刷新添加的数据（效率优化，前面的数据没必要刷新）
     *
     * @param data
     * @return 是否添加成功
     */
    public void addItemToHead(List<T> data) {
        if (data != null && data.size() > 0) {
            mList.addAll(0, data);
            /**
             *  positionStart:刷新起始位置
             *  itemCount：新增数据个数
             */
            notifyItemChanged(0, data.size());//只刷新添加的数据
        }
    }

    /**
     * 添加数据集合到最后位置(全部刷新)
     * 注：
     * 1.此方法适用于  上拉加载更多
     * 2.此方法 Adapter只会刷新添加的数据（效率优化，前面的数据没必要刷新）
     *
     * @param data
     * @return 是否添加成功
     */
    public void addItemToLastNotifyAll(List<T> data) {
        if (data != null && data.size() > 0) {
            mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据集合到最后位置(局部刷新)
     * 注：
     * 1.此方法适用于  上拉加载更多
     * 2.此方法 Adapter只会刷新添加的数据（效率优化，前面的数据没必要刷新）
     *
     * @param data
     * @return 是否添加成功
     */
    public void addItemToLast(List<T> data) {
        if (data != null && data.size() > 0) {
            mList.addAll(data);

            int sizes =mList.size();
            int size = data.size();
            if (sizes > size) {
                /**
                 *  positionStart:刷新起始位置
                 *  itemCount：新增数据个数
                 */
                notifyItemRangeChanged(sizes - size, size);//只刷新添加的数据
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 添加数据集合到指定位置（全部刷新）
     *
     * @param startPosition 数据添加的位置
     * @param data          数据集合
     */
    public void addAllNotifyAll(int startPosition, List<T> data) {
        if (data != null) {
            mList.addAll(startPosition, data);
            notifyDataSetChanged();
        }

        /**
         * ·notifyItemInserted(int position): 列表position位置添加一条数据时可以调用，伴有动画效果
         *
         * ·notifyItemRemoved(int position) :列表position位置移除一条数据时调用，伴有动画效果
         *
         * ·notifyItemMoved(int fromPosition, int toPosition) 列表fromPosition位置的数据移到toPosition位置时调用，伴有动画效果
         *
         * ·notifyItemRangeChanged(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项进行数据刷新
         *
         * ·notifyItemRangeInserted(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项批量添加数据时调用，伴有动画效果
         *
         * ·notifyItemRangeRemoved(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项批量删除数据时调用，伴有动画效果
         *
         * 需要注意的地方，在使用这些方法的时候，google官方文档中提到他们的position会自动增加，但是在实际操作过程中，并没有增加，导致数据错位的问题，所以，当我们需要使用这些特效方法的时候，必须要重新刷新一遍数据，纠正position。

         */
    }

    /**
     * 添加单个数据到指定位置(全部刷新)
     *
     * @param startPosition 数据添加的位置
     * @param data          数据
     */
    public void addAllNotifyAll(int startPosition, T data) {
        if (data != null) {
            mList.add(startPosition, data);
            notifyDataSetChanged();
        }
    }


    /**
     * 添加单个数据到指定位置(局部刷新)
     * 注：
     * 1.此方法适用于  上拉加载历史聊天内容
     * 2.此方法 Adapter只会刷新添加的数据（效率优化，前面的数据没必要刷新）
     *
     * @param datas
     * @return 是否添加成功
     */
    public void addItem(int startPosition, T datas) {
        if (datas != null) {
            mList.add(startPosition, datas);
            notifyItemInserted(startPosition);//插入一个并自动刷新
        }
    }


    /**
     * 将某一个数据修改 (全部刷新)
     *
     * @param oldData 旧的数据
     * @param newData 新的数据
     */
    public void update(T oldData, T newData) {
        update(mList.indexOf(oldData), newData);
    }

    /**
     * 修改对应的位置的数据 (全部刷新)
     *
     * @param index 修改的位置
     * @param data  要代替的的数据
     */
    public void update(int index, T data) {
        if (data != null) {
            mList.set(index, data);
            notifyItemChanged(index);
        }
    }

    /**
     * 将某一个数据修改 (全部刷新)
     *
     * @param oldData 旧的数据
     * @param newData 新的数据
     */
    public void updateNotifyAll(T oldData, T newData) {
        updateNotifyAll(mList.indexOf(oldData), newData);
    }

    /**
     * 修改对应的位置的数据 (全部刷新)
     *
     * @param index 修改的位置
     * @param data  要代替的的数据
     */
    public void updateNotifyAll(int index, T data) {
        if (data != null) {
            mList.set(index, data);
            notifyDataSetChanged();
        }

    }

    /**
     * 是否包含某个数据
     *
     * @param data
     * @return
     */
    public boolean contains(T data) {
        if (mList == null || mList.isEmpty()) return false;
        return mList.contains(data);
    }

    /**
     * 删除对应的数据
     *
     * @param data
     */
    public boolean remove(T data) {
        boolean result = false;
        if (data == null) return result;
        result = mList.remove(data);
        notifyDataSetChanged();
        return result;
    }

    /**
     * 删除对应位置的数据
     *
     * @param index
     */
    public void removeToIndex(int index) {
        if (mList == null) return;
        mList.remove(index);
        notifyDataSetChanged();
    }


    /**
     * 清除所有
     */
    public void clear() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 获取index对于的数据
     *
     * @param index 数据座标
     * @return 数据对象
     */
    public T getData(int index) {
        return getItemCount() == 0 ? null : mList.get(index);
    }

    /**
     * 获取全部数据
     *
     * @return
     */
    public List<T> getDataList() {
        return mList;
    }



    public void setOnItemClickListener(ItemListener.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(ItemListener.OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemViewClickListener(ItemListener.OnItemViewClickListener onItemViewClickListener) {
        this.mOnItemViewClickListener = onItemViewClickListener;
    }

    public ItemListener.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public ItemListener.OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public ItemListener.OnItemViewClickListener getOnItemViewClickListener() {
        return mOnItemViewClickListener;
    }

    /**
     * 注册item点击、长按事件
     *
     * @param holder
     * @param position
     */
    public void onBindItemClickListener(final V holder, final int position) {
        if (mOnItemClickListener != null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, getDataList().get(position), position);
                }
            });

        if (mOnItemLongClickListener != null)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(v, getDataList().get(position), position);
                    return true;
                }
            });
    }

}
