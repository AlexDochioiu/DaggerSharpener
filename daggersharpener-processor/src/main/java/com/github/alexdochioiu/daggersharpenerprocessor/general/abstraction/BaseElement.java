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
import com.github.alexdochioiu.daggersharpenerprocessor.general.concrete.AnnotationClass;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

/**
 * Created by Alexandru Iustin Dochioiu on 03-Feb-19
 */
public class BaseElement<T extends Element> extends BaseProcessorHelper {

    protected final T element;

    public BaseElement(
            ProcessingEnvironment processingEnvironment,
            RoundEnvironment roundEnvironment,
            Element element) {
        super(processingEnvironment, roundEnvironment);
        this.element = Preconditions.castNotNull(element);
    }

    //todo convert from AnnotationMirror
    private final List<? extends AnnotationMirror> getAnnotationMirrors() {
        return element.getAnnotationMirrors();
    }

    /**
     * @return the {@link AnnotationClass} for the desired annotation OR <b>null</b> if the desired annotation was not found
     */
    public final AnnotationClass tryGetAnnotation(final String packageName, final String annotationClassName) {
        for (final AnnotationMirror annotation : getAnnotationMirrors()) {
            // iterate all annotations applied to class

            if (processingEnvironment.getTypeUtils().isSameType(
                    annotation.getAnnotationType(),
                    Preconditions.checkNotNull(
                            processingEnvironment
                                    .getElementUtils()
                                    .getTypeElement(packageName + '.' + annotationClassName).asType()
                    )
            )) {
                return new AnnotationClass(processingEnvironment, roundEnvironment, annotation);
            }
        }
        return null;
    }
}
