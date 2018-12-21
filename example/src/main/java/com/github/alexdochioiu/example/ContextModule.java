package com.github.alexdochioiu.example;

import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alexandru Iustin Dochioiu on 21-Dec-18
 */
@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @SharpMyApplicationScope
    public Context context() {
        return context;
    }

    @Provides
    @SharpMyApplicationScope
    public Resources resources(Context context) {
        return context.getResources();
    }
}
