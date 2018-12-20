package com.github.alexdochioiu.daggersharpenerprocessor.test1res;

import com.github.alexdochioiu.daggersharpener.SharpComponent;

/**
 * Created by Alexandru Iustin Dochioiu on 18-Dec-18
 */

@SharpComponent(
        scope = OtherScope.class,
        modules = {FirstModule.class, SecondModule.class}
)
public class Application {
}