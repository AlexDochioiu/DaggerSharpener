package com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
public class ScopeUtils {
    private static TypeElement scopeElement;
    private static TypeElement retentionElement;

    private static TypeElement retentionPolicyElement;

    public static boolean init(ProcessingEnvironment processingEnvironment) {
        scopeElement = processingEnvironment
                .getElementUtils()
                .getTypeElement("javax.inject.Scope");

        retentionElement = processingEnvironment
                .getElementUtils()
                .getTypeElement("java.lang.annotation.Retention");

        retentionPolicyElement = processingEnvironment
                .getElementUtils()
                .getTypeElement("java.lang.annotation.RetentionPolicy");

        if (scopeElement == null) {
            // TODO this is not a dagger2 class
            MessagerWrapper.logError("Missing dagger2 dependency. Please add it to gradle!");
            return false;
        }
        return true;
    }

    public static TypeElement getScopeElement() {
        return scopeElement;
    }

    public static TypeElement getRetentionElement() {
        return retentionElement;
    }

    public static TypeElement getRetentionPolicyElement() {
        return retentionPolicyElement;
    }
}
