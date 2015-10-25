package me.packbag.android.ui.adapters

import me.packbag.android.R
import me.packbag.android.db.model.ItemStatus

private class CurrentAdapterCustomizer : ItemsAdapter.AdapterCustomizer {
    override fun getPopupMenuRes(): Int = R.menu.item_more_actions_current

    override fun getListItemRes(): Int = R.layout.list_item_current
}

private class TakenAdapterCustomizer : ItemsAdapter.AdapterCustomizer {
    override fun getPopupMenuRes(): Int = R.menu.item_more_actions_taken

    override fun getListItemRes(): Int = R.layout.list_item_taken
}

private class UselessAdapterCustomizer : ItemsAdapter.AdapterCustomizer {
    override fun getPopupMenuRes(): Int = R.menu.item_more_actions_useless

    override fun getListItemRes(): Int = R.layout.list_item_useless
}

object Customizers {
    @JvmStatic
    fun create(status: ItemStatus): ItemsAdapter.AdapterCustomizer = when (status) {
        ItemStatus.CURRENT -> CurrentAdapterCustomizer()
        ItemStatus.TAKEN -> TakenAdapterCustomizer()
        ItemStatus.USELESS -> UselessAdapterCustomizer()
    }
}
