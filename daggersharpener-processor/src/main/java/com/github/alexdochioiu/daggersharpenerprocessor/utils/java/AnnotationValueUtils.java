package com.github.alexdochioiu.daggersharpenerprocessor.utils.java;

import javax.lang.model.element.AnnotationValue;

/**
 * Created by Alexandru Iustin Dochioiu on 21-Dec-18
 */
public class AnnotationValueUtils {
    public static String getPackage(AnnotationValue annotationValue) {
        String classFullName = annotationValue.toString(); // includes .class at the end
        classFullName = classFullName.substring(0, classFullName.length() - 6); // remove .class at the end

        return classFullName.substring(0, classFullName.lastIndexOf("."));
    }

    public static String getSimpleName(AnnotationValue annotationValue) {
        String classFullName = annotationValue.toString(); // includes .class at the end
        classFullName = classFullName.substring(0, classFullName.length() - 6); // remove .class at the end

        return classFullName.substring(classFullName.lastIndexOf(".") + 1);
    }

    public static String getFullName(AnnotationValue annotationValue) {
        String classFullName = annotationValue.toString(); // includes .class at the end
        return classFullName.substring(0, classFullName.length() - 6); // remove .class at the end
    }
}
