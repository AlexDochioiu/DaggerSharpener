package com.github.alexdochioiu.example;

import android.app.Application;
import android.content.res.Resources;

import com.github.alexdochioiu.daggersharpener.SharpComponent;
import com.github.alexdochioiu.example.network.GithubService;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
@SharpComponent(
        modules = {GithubServiceModule.class, PicassoModule.class},
        provides = {Picasso.class, GithubService.class, Resources.class}
        )
public class MyApplication extends Application {

    private SharpMyApplicationComponent component;

    @Inject
    Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        component = DaggerSharpMyApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);

        Timber.i("%s", picasso);
    }

    public SharpMyApplicationComponent getComponent() {
        return component;
    }
}
