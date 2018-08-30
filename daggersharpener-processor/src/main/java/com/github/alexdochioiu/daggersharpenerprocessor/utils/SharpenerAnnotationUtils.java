package com.github.alexdochioiu.daggersharpenerprocessor.utils;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/29/2018
 */
public class SharpenerAnnotationUtils {
    private static TypeElement sharpComponentAnnotation;
    private static TypeElement sharpScopeAnnotation;

    public static boolean init(ProcessingEnvironment processingEnvironment) {
        sharpComponentAnnotation = processingEnvironment
                .getElementUtils()
                .getTypeElement("com.github.alexdochioiu.daggersharpener.SharpComponent");

        sharpScopeAnnotation = processingEnvironment
                .getElementUtils()
                .getTypeElement("com.github.alexdochioiu.daggersharpener.SharpScope");

        if (sharpComponentAnnotation == null) {
            MessagerWrapper.logError("Missing TeaTime (annotations) dependency. Please add it to gradle!");
            return false;
        }
        return true;
    }

    public static TypeElement getSharpComponentAnnotation() {
        return sharpComponentAnnotation;
    }

    public static TypeElement getSharpScopeAnnotation() {
        return sharpScopeAnnotation;
    }
}
