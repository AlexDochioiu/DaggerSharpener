/*
 * Copyright (c) 2019. Alexandru Iustin Dochioiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.github.alexdochioiu.daggersharpenerprocessor.general.concrete;

import com.github.alexdochioiu.daggersharpenerprocessor.general.Preconditions;
import com.github.alexdochioiu.daggersharpenerprocessor.general.abstraction.BaseAnnotationMirror;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

/**
 * Created by Alexandru Iustin Dochioiu on 06-Feb-19
 */
@SuppressWarnings("WeakerAccess")
public class AnnotationClass extends BaseAnnotationMirror {

    private final Map<String, Object> args = new HashMap<>();
    private TypeName typeName;

    public AnnotationClass(
            ProcessingEnvironment processingEnvironment,
            RoundEnvironment roundEnvironment,
            AnnotationMirror annotationMirror
    ) {
        super(processingEnvironment, roundEnvironment, annotationMirror);

        typeName = ClassName.get(annotationMirror.getAnnotationType());

        initArgs();
    }

    private void initArgs() {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                : annotationMirror.getElementValues().entrySet()) {
            String key = entry.getKey().getSimpleName().toString();
            Object value = entry.getValue().getValue();

            args.put(key, value);
        }
    }

    /**
     * @return the (cast) object found for the given argument key
     * @throws ClassCastException   when it fails to cast the object
     * @throws NullPointerException if the argument key is invalid
     */
    public <T> T getArgument(String key) {
        Preconditions.checkNotNull(key, "No key provided for annotation argument");

        return Preconditions.castNotNull(args.get(key));
    }

    /**
     * @return the (cast) object found for the given argument key OR <b>null</b> if no item was found for that key
     * @throws ClassCastException   when it fails to cast the object
     */
    public <T> T argumentOrNull(String key) {
        Preconditions.checkNotNull(key, "No key provided for annotation argument");

        return Preconditions.castIfNotNull(args.get(key));
    }
}
