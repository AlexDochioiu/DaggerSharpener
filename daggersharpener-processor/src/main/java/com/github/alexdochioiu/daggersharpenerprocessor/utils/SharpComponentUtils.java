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
import com.github.alexdochioiu.daggersharpenerprocessor.models.SharpComponentModel;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
public class SharpComponentUtils {
    public static SharpComponentModel getSharpComponentModel(final Element element, final ProcessingEnvironment processingEnvironment) {
        if (element.getKind() != ElementKind.CLASS) {
            MessagerWrapper.logError("SharpComponent annotation applies only to classes! Cannot be used with '%s'", element.getSimpleName());
            return null;
        }
        //TODO does not work with private classes! make sure this is checked

        final TypeElement typeElement = (TypeElement) element;
        final ClassName className = ClassName.get(typeElement);

        return new SharpComponentModel(className, typeElement, processingEnvironment);
    }
}
