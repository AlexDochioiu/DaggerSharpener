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

package com.github.alexdochioiu.daggersharpenerprocessor.general.concrete;

import com.github.alexdochioiu.daggersharpenerprocessor.general.abstraction.BaseElement;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 06-Feb-19
 */
@SuppressWarnings("WeakerAccess")
public class TypeClass extends BaseElement<TypeElement> {

    private ClassName className;

    public TypeClass(ProcessingEnvironment processingEnvironment, RoundEnvironment roundEnvironment, Element element) {
        super(processingEnvironment, roundEnvironment, element);

        className = ClassName.get(this.element);
    }

    public String getSimpleName() {
        return className.simpleName();
    }

    public String getPackage() {
        return className.packageName();
    }

    public ClassName getJavaPoetClassName() {
        return className;
    }
}
