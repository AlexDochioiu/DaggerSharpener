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

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

/**
 * Created by Alexandru Iustin Dochioiu on 13-Jan-19
 */
public class NamedAnnotationUtils {

    public static AnnotationSpec getNamedAnnotation(String name) {
        final ClassName namedAnnotation = ClassName.get("javax.inject", "Named");
        AnnotationSpec.Builder namedAnnotationBuilder = AnnotationSpec.builder(namedAnnotation);

        namedAnnotationBuilder.addMember("value", CodeBlock.builder().add(String.format("\"%s\"", name)).build());

        return namedAnnotationBuilder.build();
    }
}
