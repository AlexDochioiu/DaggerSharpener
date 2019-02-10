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
     * A list of sharp components (classes annotated as <b>@SharpComponent</b>) that are to be used as
     * dependencies.
     * <b>NOTE:</b> This is ONLY for the sharp components created by the developer (e.g the activity
     * class, fragment class, etc.). Do not use it with the generated files.
     */
    Class<?>[] sharpDependencies() default {};

    /**
     * A list of dagger components (classes annotated as <b>@Component</b>) that are to be used as
     * dependencies.
     * <b>NOTE:</b> This is ONLY for the components created by the developer. Do not use it with
     * generated files.
     */
    Class<?>[] dependencies() default {};

    /**
     * The list of classes/interfaces for which objects are provided by this {@link SharpComponent}
     * to other dagger/sharp components depending on it.
     * <p>
     */
    Class<?>[] provides() default {};

    /**
     * The list of {@link NamedPair} entries to be provided by this {@link SharpComponent} to other
     * dagger/sharp components depending on it. Each {@link NamedPair} will contain the name and the class
     * for a single object.
     */
    NamedPair[] providesNamed() default {};

    /**
     * A scope for the component. {@link SharpScope} will create a new scope for this component.
     */
    Class<?> scope() default SharpScope.class;
}
