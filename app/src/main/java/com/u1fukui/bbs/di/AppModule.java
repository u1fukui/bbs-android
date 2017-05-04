package com.u1fukui.bbs.di;

import com.u1fukui.bbs.repository.ThreadRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    ThreadRepository provideThreadRepository() {
        return new ThreadRepository();
    }
}
