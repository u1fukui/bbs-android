package com.u1fukui.bbs.di;

import android.content.Context;

import com.u1fukui.bbs.App;
import com.u1fukui.bbs.repository.ThreadRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context context;

    public AppModule(App app) {
        context = app;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public ThreadRepository provideThreadRepository() {
        return new ThreadRepository();
    }
}
