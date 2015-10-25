package me.packbag.android;

import javax.inject.Singleton;

import dagger.Component;
import me.packbag.android.db.DbModule;
import me.packbag.android.db.api.IDao;
import me.packbag.android.network.NetworkModule;
import me.packbag.android.network.api.Backend;
import me.packbag.android.ui.activities.ItemListActivity;

@Singleton
@Component(
        modules = {AppModule.class, DbModule.class, NetworkModule.class})
public interface AppComponent {

    Backend backend();

    IDao dao();

    void inject(ItemListActivity itemListActivity);


//	void inject(@NonNull MainActivity service);
//
//	void inject(@NonNull ProductsActivity productsActivity);
}
