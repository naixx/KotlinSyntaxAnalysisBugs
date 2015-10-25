package me.packbag.android.db.model;

import com.github.naixx.WithId;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import net.tribe7.common.base.Objects;

import java.util.LinkedHashMap;
import java.util.Map;

import me.packbag.android.db.api.Db;

@Table(databaseName = Db.NAME)
@ModelContainer
public class ItemInSet extends BaseModel implements WithId {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    @ForeignKey(
            references = { @ForeignKeyReference(
                    columnName = "item_set_id",
                    columnType = Long.class,
                    foreignColumnName = "id") },
            saveForeignKeyModel = false)
    ForeignKeyContainer<ItemSet> itemSet;

    @Column
    @ForeignKey(
            references = { @ForeignKeyReference(
                    columnName = "item_id",
                    columnType = Long.class,
                    foreignColumnName = "id") },
            saveForeignKeyModel = false)
    ForeignKeyContainer<Item> item;

    @Column ItemStatus status = ItemStatus.CURRENT;

    public void setItemSet(long id) {
        itemSet = new ForeignKeyContainer<>(ItemSet.class);
        Map<String, Object> keys = new LinkedHashMap<>();
        keys.put(ItemSet_Table.ID, id);
        itemSet.setData(keys);
    }

    public void setItem(long id) {
        item = new ForeignKeyContainer<>(Item.class);
        Map<String, Object> keys = new LinkedHashMap<>();
        keys.put(Item_Table.ID, id);
        item.setData(keys);
    }

    public ItemStatus getStatus() {
        return status;
    }

    public ItemInSet setStatus(ItemStatus status) {
        this.status = status;
        return this;
    }

    public Item getItem() {
        return item.load();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {return Objects.hashCode(id);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ItemInSet other = (ItemInSet) obj;
        return Objects.equal(this.id, other.id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("item", item).add("status", status).toString();
    }
}
