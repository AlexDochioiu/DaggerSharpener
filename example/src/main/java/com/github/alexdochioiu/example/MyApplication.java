package com.github.alexdochioiu.example;

import android.app.Application;

import com.github.alexdochioiu.daggersharpener.SharpComponent;
import com.github.alexdochioiu.example.dagger.FirstModule;
import com.github.alexdochioiu.example.dagger.OtherScope;
import com.github.alexdochioiu.example.dagger.SecondModule;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
@SharpComponent(
        scope = OtherScope.class,
        modules = {FirstModule.class, SecondModule.class}
        )
public class MyApplication extends Application {

    private SharpMyApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerSharpMyApplicationComponent.builder()
                .build();

        component.inject(this);
    }

    public SharpMyApplicationComponent getComponent() {
        return component;
    }
}
