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
package com.github.alexdochioiu.daggersharpenerprocessor.utils.java;

import javax.lang.model.element.AnnotationValue;

/**
 * Created by Alexandru Iustin Dochioiu on 21-Dec-18
 */
public class AnnotationValueUtils {
    public static String getPackage(AnnotationValue annotationValue) {
        String classFullName = annotationValue.toString(); // includes .class at the end
        classFullName = classFullName.substring(0, classFullName.length() - 6); // remove .class at the end

        return classFullName.substring(0, classFullName.lastIndexOf("."));
    }

    public static String getSimpleName(AnnotationValue annotationValue) {
        String classFullName = annotationValue.toString(); // includes .class at the end
        classFullName = classFullName.substring(0, classFullName.length() - 6); // remove .class at the end

        return classFullName.substring(classFullName.lastIndexOf(".") + 1);
    }

    public static String getFullName(AnnotationValue annotationValue) {
        String classFullName = annotationValue.toString(); // includes .class at the end
        return classFullName.substring(0, classFullName.length() - 6); // remove .class at the end
    }
}
