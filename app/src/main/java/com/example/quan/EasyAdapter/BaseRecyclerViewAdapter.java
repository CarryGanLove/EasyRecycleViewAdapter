package com.example.quan.EasyAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by GanQuan on 16/3/6.
 */
public abstract class BaseRecyclerViewAdapter<T> extends
        RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<T>> {
    private final String TAG = getClass().getSimpleName();
    private List<T> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Class<?>> mViewBundles;
    private OnItemClickListener mOnItemClickLitener;
    private OnItemLongClickListener mOnItemLongClickLitener;

    public interface OnItemClickListener<T> {
        void onItemClick(T t, View view, int position);

    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(T t, View view, int position);
    }

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);


    }

    public void setOnItemClickLitener(OnItemClickListener<T> onItemClickLitener) {
        this.mOnItemClickLitener = onItemClickLitener;
    }

    public void setmOnItemLongClickLitener(OnItemLongClickListener<T> onItemLongClickLitener) {

        this.mOnItemLongClickLitener = onItemLongClickLitener;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mViewBundles == null) {
            mViewBundles = new ArrayList<>();
            onBindVHLayoutId(mViewBundles);
        }
        if (mViewBundles.size() > 0) {
            Class viewBundle = findViewHolderClazz(viewType);
            int layoutId = BindLayoutMapping.getLayoutId(viewBundle);
            View view = mInflater.inflate(layoutId, parent, false);// todo
            return (BaseViewHolder<T>) InstanceUtil.getInstance(viewBundle, new Class[]{View.class},
                    new Object[]{view});

        } else {

            throw new IllegalArgumentException("mviewbundles can not be null or empty!");
        }
    }

    private Class findViewHolderClazz(int viewType) {
        return mViewBundles.get(viewType);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder<T> holder, final int position) {
        try {
            holder.bindView(mList.get(position), position, mContext);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(mList.get(position), holder.itemView, holder.getLayoutPosition());
                }
            });

        }
        if (mOnItemLongClickLitener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickLitener.onItemLongClick(mList.get(position), holder.itemView,
                            holder.getLayoutPosition());
                    return false;
                }
            });

        }
    }

    /**
     * 绑定 vh和layoutid
     * <p>
     * 该list的index表示对应的view type
     */
    protected abstract void onBindVHLayoutId(List<Class<?>> VhClazzList);

    /**
     * 如果viewbundles size=1 不需要重写该函数
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public T getItemBean(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();

    }

    public void initList(List<T> list) {
        if (mList.size() != 0)
            mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearList() {
        mList.clear();
    }

    public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        public Context getContext() {
            return itemView.getContext();
        }

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        /**
         * 设置该viewHolder上数据
         *
         * @param bean
         * @param position
         * @param context
         */
        protected abstract void bindView(T bean, int position, Context context) throws ParseException;

    }

    public static class ViewBundle {
        public ViewBundle(int layoutId, Class<? extends BaseViewHolder> clazz) {
            this.layoutId = layoutId;
            this.VHclazz = clazz;
        }

        public int layoutId;
        public Class VHclazz;
    }
}
