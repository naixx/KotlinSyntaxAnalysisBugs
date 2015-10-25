package me.packbag.android.ui.events

import me.packbag.android.db.model.ItemInSet
import me.packbag.android.db.model.ItemStatus

data class ItemStatusChangedEvent(val itemInSet: ItemInSet, val status: ItemStatus)

