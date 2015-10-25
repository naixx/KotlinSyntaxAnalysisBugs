package me.packbag.android.db.model;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
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
public class Item extends BaseModel implements WithId {

    @Column
    @PrimaryKey(autoincrement = true)
    @JsonIgnore
    long id;

    @Column
    @JsonProperty("id")
    long serverId;

    @JsonProperty("name")
    @Column
    String name;

    @Column
    @ForeignKey(
            references = { @ForeignKeyReference(
                    columnName = "item_category_id",
                    columnType = Long.class,
                    foreignColumnName = "id") },
            saveForeignKeyModel = false)
    ForeignKeyContainer<ItemCategory> category;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getServerId() {
        return serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(ItemCategory category) {
        this.category = new ForeignKeyContainer<>(ItemCategory.class);
        this.category.setModel(category);
    }

    @JsonSetter("item_category_id")
    public void setCategory(long id) {
        category = new ForeignKeyContainer<>(ItemCategory.class);
        Map<String, Object> keys = new LinkedHashMap<>();
        keys.put(ItemCategory_Table.ID, id);
        category.setData(keys);
    }

    @NonNull
    public ItemCategory getCategory() {
        return category.load();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("serverId", serverId).add("name", name).toString();
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
        final Item other = (Item) obj;
        return Objects.equal(this.id, other.id);
    }
}
