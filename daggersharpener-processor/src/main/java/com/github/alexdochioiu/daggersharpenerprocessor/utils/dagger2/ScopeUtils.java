package com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
public class ScopeUtils {
    private static TypeElement scopeElement;

    public static boolean init(ProcessingEnvironment processingEnvironment) {
        scopeElement = processingEnvironment
                .getElementUtils()
                .getTypeElement("javax.inject.Scope");

        if (scopeElement == null) {
            MessagerWrapper.logError("Missing dagger2 dependency. Please add it to gradle!");
            return false;
        }
        return true;
    }

    public static TypeElement getScopeElement() {
        return scopeElement;
    }
}
