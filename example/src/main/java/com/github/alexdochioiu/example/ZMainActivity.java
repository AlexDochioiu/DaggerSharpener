package com.github.alexdochioiu.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.alexdochioiu.daggersharpener.SharpComponent;

@SharpComponent(sharpDependencies = MyApplication.class)
public class ZMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerSharpZMainActivityComponent.builder()
                .sharpMyApplicationComponent(((MyApplication) getApplication()).getComponent())
                .build()
                .inject(this);
    }
}
