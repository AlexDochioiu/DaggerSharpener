package com.github.alexdochioiu.example;

import android.app.Application;

import com.github.alexdochioiu.daggersharpener.SharpComponent;
import com.github.alexdochioiu.daggersharpener.SharpScope;
import com.github.alexdochioiu.example.sc.OtherScope;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
@SharpComponent(scope = OtherScope.class)
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DaggerSharpMyApplicationComponent.builder()
                .build()
                .inject(this);

    }
}