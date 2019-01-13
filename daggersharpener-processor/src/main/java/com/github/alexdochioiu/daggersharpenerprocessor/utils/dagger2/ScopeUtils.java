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
            MessagerWrapper.logWarning("ScopeUtils: Missing dagger2 dependency. Please add it to gradle!");
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
