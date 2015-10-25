package com.github.naixx;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by astra on 23.10.2015.
 */
public abstract class BindingBaseViewHolder<T extends WithId, B extends ViewDataBinding> extends RecyclerView.ViewHolder {

    final B binding;

    public BindingBaseViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public abstract void bind(B binding, T item, int position);
}
