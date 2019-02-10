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

package com.github.alexdochioiu.daggersharpenerprocessor.general.abstraction;

import com.github.alexdochioiu.daggersharpenerprocessor.general.Preconditions;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 05-Feb-19
 */
public class BaseAnnotationMirror<T extends AnnotationMirror> extends BaseProcessorHelper {

    protected final T annotationMirror;

    public BaseAnnotationMirror(
            ProcessingEnvironment processingEnvironment,
            RoundEnvironment roundEnvironment,
            AnnotationMirror annotationMirror) {
        super(processingEnvironment, roundEnvironment);
        this.annotationMirror = Preconditions.castNotNull(annotationMirror);
    }

    public boolean isSameAnnotation(TypeMirror otherAnnotation) {
        return processingEnvironment.getTypeUtils().isSameType(annotationMirror.getAnnotationType(), otherAnnotation);
    }
}
