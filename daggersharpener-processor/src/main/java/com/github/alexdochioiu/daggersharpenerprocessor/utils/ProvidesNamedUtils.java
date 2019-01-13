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
import com.github.alexdochioiu.daggersharpenerprocessor.utils.java.NamedTypeMirror;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 12-Jan-19
 */
public class ProvidesNamedUtils {

    public static List<NamedTypeMirror> getNamedProvides(final List<? extends AnnotationValue> annotationValues) {
        final List<NamedTypeMirror> namedProvidesList = new LinkedList<>();

        for (final AnnotationValue providesNamedAnnotation : annotationValues) {
            // gives each of: @NamedPair(aName = "myName", aClass = MyClass.class)

            final NamedTypeMirror.Builder namedPairBuilder = new NamedTypeMirror.Builder();
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> namedAnnotationArgs :
                    ((AnnotationMirror) providesNamedAnnotation).getElementValues().entrySet()) {
                // gives the args for @NamedPair. Example: {aName = "myName"}

                String key = namedAnnotationArgs.getKey().getSimpleName().toString(); //eg aName
                Object value = namedAnnotationArgs.getValue().getValue(); //eg "myName:"

                switch (key) {
                    case "aName":
                        namedPairBuilder.withName((String) value);
                        break;
                    case "aClass":
                        namedPairBuilder.withTypeMirror((TypeMirror) value);
                        break;
                    default:
                        MessagerWrapper.logError("Unknown parameter '%s' on @NamedPair", key);
                }
            }

            if (!namedPairBuilder.hasNameAndTypeMirror()) {
                MessagerWrapper.logError(">> problem with the following @ProvidesNamed: %s", providesNamedAnnotation);
            } else {
                namedProvidesList.add(namedPairBuilder.build());
            }
        }

        return namedProvidesList;
    }

}
