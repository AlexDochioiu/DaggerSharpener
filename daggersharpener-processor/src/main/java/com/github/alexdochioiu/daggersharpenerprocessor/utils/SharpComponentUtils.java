package com.github.alexdochioiu.daggersharpenerprocessor.utils;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;
import com.github.alexdochioiu.daggersharpenerprocessor.models.SharpComponentModel;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
public class SharpComponentUtils {
    public static SharpComponentModel getSharpComponentModel(final Element element, final ProcessingEnvironment processingEnvironment) {
        if (element.getKind() != ElementKind.CLASS) {
            MessagerWrapper.logError("SharpComponent annotation applies only to classes! Cannot be used with '%s'", element.getSimpleName());
            return null;
        }
        //TODO does not work with private classes! make sure this is checked

        final TypeElement typeElement = (TypeElement) element;
        final ClassName className = ClassName.get(typeElement);

        return new SharpComponentModel(className, typeElement, processingEnvironment);
    }
}
