package com.github.alexdochioiu.example;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Alexandru Iustin Dochioiu on 8/30/2018
 */
@Scope
@Retention(value = RetentionPolicy.CLASS)
public @interface MyScope {
}
