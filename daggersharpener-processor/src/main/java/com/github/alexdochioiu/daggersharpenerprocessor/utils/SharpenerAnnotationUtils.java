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

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/29/2018
 */
public class SharpenerAnnotationUtils {
    private static TypeElement sharpComponentAnnotation;
    private static TypeElement sharpScopeAnnotation;

    public static boolean init(ProcessingEnvironment processingEnvironment) {
        sharpComponentAnnotation = processingEnvironment
                .getElementUtils()
                .getTypeElement("com.github.alexdochioiu.daggersharpener.SharpComponent");

        sharpScopeAnnotation = processingEnvironment
                .getElementUtils()
                .getTypeElement("com.github.alexdochioiu.daggersharpener.SharpScope");

        if (sharpComponentAnnotation == null) {
            MessagerWrapper.logError("Missing TeaTime (annotations) dependency. Please add it to gradle!");
            return false;
        }
        return true;
    }

    public static TypeElement getSharpComponentAnnotation() {
        return sharpComponentAnnotation;
    }

    public static TypeElement getSharpScopeAnnotation() {
        return sharpScopeAnnotation;
    }
}
