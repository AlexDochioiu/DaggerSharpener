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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.AnnotationValue;

/**
 * Created by Alexandru Iustin Dochioiu on 03-Feb-19
 *
 * Based on com.google.common.base.Preconditions
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class Preconditions {

    public static <T> T checkNotNull(final T reference) throws NullPointerException {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(final T reference, final String failMessage) throws NullPointerException {
        if (reference == null) {
            throw new NullPointerException(failMessage);
        } else {
            return reference;
        }
    }

    public static <T, Y> T castNotNull(final Y reference) throws ClassCastException, NullPointerException {
        //noinspection unchecked : Throwing on fail cast is the desired behaviour
        return (T) checkNotNull(reference);
    }

    public static <T, Y> T castIfNotNull(final Y reference) throws ClassCastException {
        if (reference != null) {
            //noinspection unchecked : Throwing on fail cast is the desired behaviour
            return (T) checkNotNull(reference);
        } else {
            return null;
        }
    }

    public static <T> Collection<T> emptyOnNull(final Collection<T> reference) {
        if (reference != null) {
            return reference;
        } else {
            return Collections.emptyList();
        }
    }
}
