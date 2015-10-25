package me.packbag.android.network;

import com.raizlabs.android.dbflow.annotation.NotNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.packbag.android.BuildConfig;
import me.packbag.android.network.api.Backend;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

/**
 * Created by astra on 12.07.2015.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    @NotNull
    @Named("hikapro")
    RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder().setEndpoint("")
                .setConverter(new JacksonConverter())
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .build();
    }

    @Provides
    @Singleton
    @NotNull
    Backend provideBackend(@Named("hikapro") RestAdapter restAdapter) {
        return restAdapter.create(Backend.class);
    }
}
