package com.github.alexdochioiu.daggersharpenerprocessor.utils;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;
import com.github.alexdochioiu.daggersharpenerprocessor.models.SharpScopeModel;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2.ScopeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 8/30/2018
 */
public class ScopeClassUtils {
    /**
     * @return <b>NULL</b> if it's not sharp scope or scope @interface
     * OR the {@link SharpScopeModel} otherwise
     */
    public static SharpScopeModel getSharpScopeModel(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        if (isAnnotationClass(typeMirror, processingEnvironment)) {
            // it is @interface type as expected
            Element element = processingEnvironment.getTypeUtils().asElement(typeMirror);

            if (processingEnvironment.getTypeUtils().isSameType(typeMirror, SharpenerAnnotationUtils.getSharpScopeAnnotation().asType())) {
                // it is sharp scoped

                return new SharpScopeModel();

            } else {
                // check if we have the @Scope annotation for this @interface

                boolean foundScopeAnnotation = false;
                // check all the annotations and see whether it is an @Scope
                for (AnnotationMirror mirror : element.getAnnotationMirrors()) {
                    if (processingEnvironment.getTypeUtils().isSameType(mirror.getAnnotationType(), ScopeUtils.getScopeElement().asType())) {
                        foundScopeAnnotation = true;
                        break;
                    }
                }

                if (foundScopeAnnotation) {
                    return new SharpScopeModel(typeMirror);
                } else {
                    MessagerWrapper.logError("'%s' is given as a Scope class but it does not have the '@Scope' annotation", element.getSimpleName());
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    private static boolean isAnnotationClass(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        try {
            Element element = processingEnvironment.getTypeUtils().asElement(typeMirror);

            return element.getKind() == ElementKind.ANNOTATION_TYPE;
        } catch (Exception e) {
            MessagerWrapper.logError("Could not convert %s to element type. Is it an annotation?", typeMirror.getClass().getSimpleName());
            return false;
        }
    }
}
