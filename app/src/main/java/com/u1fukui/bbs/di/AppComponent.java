package com.u1fukui.bbs.di;

import com.u1fukui.bbs.App;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, InjectorsModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {

        public abstract void appModule(AppModule activityModule);

        @Override
        public void seedInstance(App instance) {
            appModule(new AppModule(instance));
        }
    }
}
