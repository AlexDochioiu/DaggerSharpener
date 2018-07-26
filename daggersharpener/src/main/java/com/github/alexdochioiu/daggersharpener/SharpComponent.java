/*
 * Copyright 2018 Alexandru Iustin Dochioiu
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
package com.github.alexdochioiu.daggersharpener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface SharpComponent {
    /**
     * A list of classes annotated with dagger2 <b>Module</b> whose bindings are used to generate the
     * component implementation.
     */
    Class<?>[] modules() default {};

    /**
     * A list of types that are to be used as component dependencies.
     */
    Class<?>[] dependencies() default {};

    /**
     * A scope for the component. If no scope is given, one would be created automatically and used
     * for this sharp component.
     */
    Class<?> scope() default SharpScope.class;
}
