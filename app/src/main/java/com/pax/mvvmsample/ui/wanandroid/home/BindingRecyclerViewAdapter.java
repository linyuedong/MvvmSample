package com.pax.mvvmsample.ui.wanandroid.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.Utils.LogUtlis;

import java.lang.ref.WeakReference;
import java.util.Collection;

public class BindingRecyclerViewAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    protected OnItemClickListener<T> mOnItemClickListener;
    private static final int ITEM_MODEL = -124;
    private final WeakReferenceOnListChangedCallback onListChangedCallback;
    private OnItemLongClickListener<T> mOnItemLongClickListener;
    private final LayoutInflater mLayoutInflater;
    private ObservableList<T> items;
    private LayoutInflater inflater;
    private View headView;
    private final int HEAD_VIEW_TYPE = 1;
    private final int NORMAL_ITEM_TYPE = 2;
    private int mLayoutId;
    private Context mContext;
    private int mHeadViewLayoutId;
    private int mItemVariableId;

    public  ViewDataBinding mHeadBinding;

    public BindingRecyclerViewAdapter(Context context, @Nullable Collection<T> items, int layoutId,int itemVariableId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemVariableId = itemVariableId;
        this.onListChangedCallback = new WeakReferenceOnListChangedCallback<>(this);
        setItems(items);

    }


    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        ViewDataBinding binding;
        if (viewType == HEAD_VIEW_TYPE) {
            mHeadBinding = binding = DataBindingUtil.inflate(mLayoutInflater, mHeadViewLayoutId, viewGroup, false);
            if(mHeadBinding == null){
                LogUtlis.i("onCreateViewHolder mHeadBinding == null");
            }else{
                LogUtlis.i("onCreateViewHolder mHeadBinding != null");

            }
        } else {
            binding = DataBindingUtil.inflate(mLayoutInflater, mLayoutId, viewGroup, false);

        }
        return new BindingViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder bindingViewHolder, int position) {
        if (getItemViewType(position) == HEAD_VIEW_TYPE){
            mHeadBinding = bindingViewHolder.getBinding();
            if(mHeadBinding == null){
                LogUtlis.i("onBindViewHolder mHeadBinding == null");
            }{
                LogUtlis.i("onBindViewHolder mHeadBinding != null");

            }
            bindingViewHolder.getBinding().setVariable(headId,headvm);
            bindingViewHolder.getBinding().executePendingBindings();
            return;
        }
        final T item = items.get(getRealPosition(position));
        bindingViewHolder.getBinding().setVariable(mItemVariableId,item);
        bindingViewHolder.getBinding().getRoot().setTag(ITEM_MODEL, item);
        bindingViewHolder.getBinding().getRoot().setOnClickListener(this);
        bindingViewHolder.getBinding().getRoot().setOnLongClickListener(this);
        bindingViewHolder.getBinding().executePendingBindings();


    }


    private int getRealPosition(int position) {
        if (headView != null) {
            return position - 1;
        }

        return position;
    }


    @Override
    public int getItemCount() {
        return headView == null ? items.size() : items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView != null && (position == 0)) {
            return HEAD_VIEW_TYPE;
        }
        return NORMAL_ITEM_TYPE;
    }

    public void setHeaderView(int headViewLayoutId) {
        View view = null;
        try {
            view = mLayoutInflater.inflate(headViewLayoutId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(view != null){
            headView = view;
            mHeadViewLayoutId = headViewLayoutId;
        }
        notifyDataSetChanged();
    }

    private Object headvm;
    private int headId;

    public void setHeaderView(int headViewLayoutId,Object headvm, int headId) {
        View view = null;
        try {
            view = mLayoutInflater.inflate(headViewLayoutId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(view != null){
            this.headView = view;
            this.mHeadViewLayoutId = headViewLayoutId;
            this.headvm = headvm;
            this.headId = headId;
        }
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return headView;
    }


    public ObservableList<T> getItems() {
        return items;
    }

    public void setItems(@Nullable Collection<T> items) {
        if (this.items == items) {
            return;
        }

        if (this.items != null) {
            this.items.removeOnListChangedCallback(onListChangedCallback);
            notifyItemRangeRemoved(0, this.items.size());
        }

        if (items instanceof ObservableList) {
            this.items = (ObservableList<T>) items;
            notifyItemRangeInserted(0, this.items.size());
            this.items.addOnListChangedCallback(onListChangedCallback);
        } else if (items != null) {
            this.items = new ObservableArrayList<>();
            this.items.addOnListChangedCallback(onListChangedCallback);
            this.items.addAll(items);
        } else {
            this.items = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null)
        {
            T item = (T) v.getTag(ITEM_MODEL);
            mOnItemClickListener.onClick(item);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null)
        {
            T item = (T) v.getTag(ITEM_MODEL);
            mOnItemLongClickListener.onLongClick(item);
            return true;
        }
        return false;
    }


    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback {

        private final WeakReference<BindingRecyclerViewAdapter<T>> adapterReference;

        public WeakReferenceOnListChangedCallback(BindingRecyclerViewAdapter<T> bindingRecyclerViewAdapter) {
            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public static interface OnItemLongClickListener<T> {

        public void onLongClick(T t);
    }

    public static interface OnItemClickListener<T> {

        public void onClick(T t);
    }

}
