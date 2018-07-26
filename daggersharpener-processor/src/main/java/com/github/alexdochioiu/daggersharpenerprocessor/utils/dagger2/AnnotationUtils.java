package com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
public class AnnotationUtils {
    private static TypeElement daggerComponentTypeElement;

    public static boolean init(ProcessingEnvironment processingEnvironment) {
        daggerComponentTypeElement = processingEnvironment
                .getElementUtils()
                .getTypeElement("dagger.Component");

        if (daggerComponentTypeElement == null) {
            MessagerWrapper.logError("Missing dagger2 dependency. Please add it to gradle!");
            return false;
        }
        return true;
    }

    public static AnnotationSpec getDaggerComponentAnnotation() {
        return AnnotationSpec.builder(ClassName.get(daggerComponentTypeElement))
                .build();
    }
}
