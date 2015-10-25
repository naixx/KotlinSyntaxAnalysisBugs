package me.packbag.android.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.naixx.WithId;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import net.tribe7.common.base.Objects;

import java.util.List;

import me.packbag.android.db.api.Db;

/**
 * Created by astra on 22.05.2015.
 */
@ModelContainer
@Table(databaseName = Db.NAME)
public class ItemCategory extends BaseModel implements WithId {

    public static final long USER = -1;

    @Column
    @PrimaryKey
    @JsonProperty("id")
    long id;

    @Column
    @JsonProperty("name")
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    List<Item> items;

    @OneToMany(methods = { OneToMany.Method.SAVE, OneToMany.Method.DELETE }, variableName = "items")
    public List<Item> getItems() {
        if (items == null) {
            items = new Select().from(Item.class)
                    .where(Condition.column(Item_Table.CATEGORY_ITEM_CATEGORY_ID).is(id))
                    .queryList();
        }
        return items;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("name", name).toString();
    }
}
