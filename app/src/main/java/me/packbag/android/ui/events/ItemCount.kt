package me.packbag.android.ui.events

import me.packbag.android.db.model.ItemStatus

data class ItemCount(val status: ItemStatus, val count: Int)
