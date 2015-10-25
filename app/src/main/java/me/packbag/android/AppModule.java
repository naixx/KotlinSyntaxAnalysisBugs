package me.packbag.android;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @NonNull private final App app;

    AppModule(@NonNull App app) {
        this.app = app;
    }

    @Provides
    @NonNull
    @Singleton
    Context provideContext() {
        return app;
    }
}
