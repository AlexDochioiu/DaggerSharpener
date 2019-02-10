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

package com.github.alexdochioiu.daggersharpenerprocessor.general;

import com.github.alexdochioiu.daggersharpenerprocessor.general.concrete.AnnotationClass;
import com.github.alexdochioiu.daggersharpenerprocessor.general.concrete.TypeClass;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

/**
 * Created by Alexandru Iustin Dochioiu on 03-Feb-19
 */
public class GeneralHelper {
    public final ProcessingEnvironment processingEnvironment;
    public final RoundEnvironment roundEnvironment;

    public GeneralHelper(ProcessingEnvironment processingEnvironment, RoundEnvironment roundEnvironment) {
        this.processingEnvironment = processingEnvironment;
        this.roundEnvironment = roundEnvironment;
    }


    public TypeClass makeTypeClass(Element element) {
        Preconditions.checkNotNull(processingEnvironment, "GeneralHelper.processingEnvironment should have been initialized!");
        Preconditions.checkNotNull(roundEnvironment, "GeneralHelper.roundEnvironment should have been initialized!");
        return new TypeClass(processingEnvironment, roundEnvironment, element);
    }

    public AnnotationClass makeAnnotationClass(AnnotationMirror annotationMirror) {
        Preconditions.checkNotNull(processingEnvironment, "GeneralHelper.processingEnvironment should have been initialized!");
        Preconditions.checkNotNull(roundEnvironment, "GeneralHelper.roundEnvironment should have been initialized!");
        return new AnnotationClass(processingEnvironment, roundEnvironment, annotationMirror);
    }

    public Set<? extends Element> getElementsAnnotatedWith(final String packageName, final String className) {
        return Preconditions.checkNotNull(
                roundEnvironment.getElementsAnnotatedWith(
                        processingEnvironment.getElementUtils()
                                .getTypeElement(packageName + '.' + className)
                )
        );
    }
}
