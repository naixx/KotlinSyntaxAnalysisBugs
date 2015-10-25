package me.packbag.android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.naixx.BindingBaseAdapter
import com.github.naixx.BindingBaseViewHolder

import me.packbag.android.R
import me.packbag.android.databinding.ListItemSetBinding
import me.packbag.android.db.model.ItemSet

/**
 * Created by astra on 17.07.2015.
 */
class ItemSetAdapter(private val listener: BindingBaseAdapter.InteractionListener<ItemSet>) :
        BindingBaseAdapter<ItemSet, ItemSetAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : BindingBaseViewHolder<ItemSet, ListItemSetBinding>(itemView) {

        override fun bind(binding: ListItemSetBinding, item: ItemSet, position: Int) {
            binding.setItemSet(item)
            itemView.setOnClickListener { listener.onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_set, parent, false))
    }
}
