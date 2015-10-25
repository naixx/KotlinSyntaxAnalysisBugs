package me.packbag.android.db.api;

import java.util.List;

import me.packbag.android.db.model.Item;
import me.packbag.android.db.model.ItemCategory;
import me.packbag.android.db.model.ItemInSet;
import me.packbag.android.db.model.ItemSet;
import rx.Observable;
import rx.Single;

/**
 * Created by astra on 24.10.2015.
 */
public interface IDao {
    Observable<List<ItemSet>> itemSets();

    Single<List<ItemInSet>> itemsInSets(ItemSet itemSet);

    Single<List<ItemCategory>> categories(ItemSet itemSet);

    Observable<Item> itemsExcludingItemSet(ItemSet itemSet);

    Single<List<Item>> itemsAll();

    void clearItems(ItemSet itemSet);
}
