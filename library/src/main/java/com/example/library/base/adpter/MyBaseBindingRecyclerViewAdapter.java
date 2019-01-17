package com.example.library.base.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.Utils.LogUtlis;

import java.lang.ref.WeakReference;

public abstract class MyBaseBindingRecyclerViewAdapter<T> extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {
    protected OnItemClickListener<T> mOnItemClickListener;
    private static final int ITEM_MODEL = -124;
    private final WeakReferenceOnListChangedCallback onListChangedCallback;
    private OnItemLongClickListener<T> mOnItemLongClickListener;
    private final LayoutInflater mLayoutInflater;
    private ObservableList<T> items;
    private View headView;
    private final int HEAD_VIEW_TYPE = 1;
    private final int NORMAL_ITEM_TYPE = 2;
    private Context mContext;


    public View view;

    public MyBaseBindingRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onListChangedCallback = new WeakReferenceOnListChangedCallback<>(this);
        items = new ObservableArrayList<>();
        notifyItemRangeInserted(0, this.items.size());
        this.items.addOnListChangedCallback(onListChangedCallback);

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == HEAD_VIEW_TYPE) {
            return new HeadViewHolder(headView);
        } else {
            ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, this.getLayoutResId(viewType), viewGroup, false);
            return new BindingViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == HEAD_VIEW_TYPE) {
            return;
        }
        final T item = items.get(getRealPosition(position));
        BindingViewHolder bindingViewHolder = (BindingViewHolder) viewHolder;
        bindingViewHolder.getBinding().setVariable(this.getItemVariableId(), item);
        bindingViewHolder.getBinding().getRoot().setTag(ITEM_MODEL, item);
        bindingViewHolder.getBinding().getRoot().setOnClickListener(this);
        bindingViewHolder.getBinding().getRoot().setOnLongClickListener(this);
        bindingViewHolder.getBinding().executePendingBindings();


    }

    protected abstract @LayoutRes
    int getLayoutResId(int viewType);

    protected abstract @LayoutRes
    int getItemVariableId();

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

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);


        }
    }


    public void setHeaderView(View view) {
        this.headView = view;
        notifyDataSetChanged();
    }


    public View getHeaderView() {
        return headView;
    }


    public void setItems(@Nullable ObservableList<T> items) {
        if (this.items == items) {
            return;
        }

        if (this.items != null) {
            this.items.removeOnListChangedCallback(onListChangedCallback);
            notifyItemRangeRemoved(0, this.items.size());
        }
        this.items =  items;
        notifyItemRangeInserted(0, this.items.size());
        this.items.addOnListChangedCallback(onListChangedCallback);
    }

    public ObservableList<T> getItems() {
        return items;
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            T item = (T) v.getTag(ITEM_MODEL);
            mOnItemClickListener.onClick(item);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            T item = (T) v.getTag(ITEM_MODEL);
            mOnItemLongClickListener.onLongClick(item);
            return true;
        }
        return false;
    }


    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback {

        private final WeakReference<MyBaseBindingRecyclerViewAdapter<T>> adapterReference;

        public WeakReferenceOnListChangedCallback(MyBaseBindingRecyclerViewAdapter<T> myBaseBindingRecyclerViewAdapter) {
            this.adapterReference = new WeakReference<>(myBaseBindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                LogUtlis.i("1");
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
                LogUtlis.i("2");
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null ) {
                if(((MyBaseBindingRecyclerViewAdapter) adapter).headView != null){
                    //注意要加1
                    adapter.notifyItemRangeRemoved(positionStart + 1, itemCount);
                }else{
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);

                }

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
