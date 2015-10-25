package me.packbag.android.db;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.packbag.android.db.api.IDao;
import me.packbag.android.db.model.Item;
import me.packbag.android.db.model.ItemCategory;
import me.packbag.android.db.model.ItemInSet;
import me.packbag.android.db.model.ItemSet;
import rx.Observable;
import rx.Single;

@Module
public class DbModule {

    @Provides
    @Singleton
    @NonNull
    IDao provideDao() {
        return new IDao() {
            @Override
            public Observable<List<ItemSet>> itemSets() {
                return null;
            }

            @Override
            public Single<List<ItemInSet>> itemsInSets(ItemSet itemSet) {
                return null;
            }

            @Override
            public Single<List<ItemCategory>> categories(ItemSet itemSet) {
                return null;
            }

            @Override
            public Observable<Item> itemsExcludingItemSet(ItemSet itemSet) {
                return null;
            }

            @Override
            public Single<List<Item>> itemsAll() {
                return null;
            }

            @Override
            public void clearItems(ItemSet itemSet) {

            }
        };
    }

//    @Provides
//    @NonNull
//    @Singleton
//    PackDatabase provideDb(Context context) {
//        return new PackDatabase();
//    }

//    @Provides
//    @NonNull
//    @Singleton
//    DatabaseDao provideDBDao(PackDatabase db) {
//        return db.getDatabaseDao();
//    }
//
//    @Provides
//    @NonNull
//    @Singleton
//    Dao provideDao(DatabaseDao dao) {
//        return new DaoImpl(dao);
//    }
}
