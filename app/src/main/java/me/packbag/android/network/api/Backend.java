package me.packbag.android.network.api;

import java.util.List;

import me.packbag.android.db.model.Item;
import me.packbag.android.db.model.ItemCategory;
import me.packbag.android.db.model.ItemSet;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by astra on 12.07.2015.
 */
public interface Backend {

    @GET("/item_categories")
    Observable<List<ItemCategory>> itemCategories();

    @GET("/items")
    Observable<List<Item>> items();

    @GET("/sets")
    Observable<List<ItemSet>> sets();
}
