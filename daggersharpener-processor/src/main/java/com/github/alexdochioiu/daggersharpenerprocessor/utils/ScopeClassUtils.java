/*
 * Copyright 2019 Alexandru Iustin Dochioiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.alexdochioiu.daggersharpenerprocessor.utils;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;
import com.github.alexdochioiu.daggersharpenerprocessor.models.SharpScopeModel;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2.ScopeUtils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * Created by Alexandru Iustin Dochioiu on 8/30/2018
 */
public class ScopeClassUtils {
    /**
     * @return <b>NULL</b> if it's not sharp scope or scope @interface
     * OR the {@link SharpScopeModel} otherwise
     */
    public static SharpScopeModel getSharpScopeModel(TypeMirror typeMirror, final Types typeUtils) {
        if (isAnnotationClass(typeMirror, typeUtils)) {
            // it is @interface type as expected
            Element element = typeUtils.asElement(typeMirror);

            if (typeUtils.isSameType(typeMirror, SharpenerAnnotationUtils.getSharpScopeAnnotation().asType())) {
                // it is sharp scoped

                return new SharpScopeModel();

            } else {
                // check if we have the @Scope annotation for this @interface

                boolean foundScopeAnnotation = false;
                // check all the annotations and see whether it is an @Scope
                for (AnnotationMirror mirror : element.getAnnotationMirrors()) {
                    if (typeUtils.isSameType(mirror.getAnnotationType(), ScopeUtils.getScopeElement().asType())) {
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

    private static boolean isAnnotationClass(TypeMirror typeMirror, Types typeUtils) {
        try {
            Element element = typeUtils.asElement(typeMirror);

            return element.getKind() == ElementKind.ANNOTATION_TYPE;
        } catch (Exception e) {
            MessagerWrapper.logError("Could not convert %s to element type. Is it an annotation?", typeMirror.getClass().getSimpleName());
            return false;
        }
    }
}
