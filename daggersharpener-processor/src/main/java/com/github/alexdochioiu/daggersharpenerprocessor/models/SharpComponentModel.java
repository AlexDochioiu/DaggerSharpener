/*
 * Copyright (c) 2019 Alexandru Iustin Dochioiu
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
 *
 */

package com.github.alexdochioiu.daggersharpenerprocessor.models;

import com.github.alexdochioiu.daggersharpenerprocessor.general.Preconditions;
import com.github.alexdochioiu.daggersharpenerprocessor.general.concrete.AnnotationClass;
import com.github.alexdochioiu.daggersharpenerprocessor.general.concrete.TypeClass;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.ProvidesNamedUtils;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.SharpEnvConstants;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.java.NamedTypeMirror;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 10-Feb-19
 */
public class SharpComponentModel {
    // the class annotated as @SharpComponent
    public final TypeClass annotatedClass;

    public final SharpScopeModel scope;

    private final List<AnnotationValue> componentDependencies = new LinkedList<>();
    private final List<AnnotationValue> componentSharpDependencies = new LinkedList<>();
    private final List<AnnotationValue> componentModules = new LinkedList<>();
    private final List<AnnotationValue> componentProvides = new LinkedList<>();
    private final List<NamedTypeMirror> componentProvidesNamed = new LinkedList<>();


    /**
     * Constructor
     *
     * @param typeClass the class annotated as sharp component
     */
    public SharpComponentModel(final TypeClass typeClass) {
        Preconditions.checkNotNull(typeClass);
        annotatedClass = typeClass;

        final AnnotationClass sharpComponentAnnotation =
                Preconditions.checkNotNull(
                        annotatedClass.tryGetAnnotation(
                                SharpEnvConstants.containedPackage,
                                SharpEnvConstants.sharpComponentClassName
                        )
                );


        final TypeMirror sharpScopeArg = sharpComponentAnnotation.argumentOrNull("scope");
        scope = sharpScopeArg != null ? new SharpScopeModel(sharpScopeArg) : new SharpScopeModel();

        componentModules.addAll(Preconditions.emptyOnNull(
                sharpComponentAnnotation.<List<? extends AnnotationValue>>argumentOrNull("modules"))
        );

        // THIS IS FOR MANUALLY CREATED DEPENDENCIES (not sharp dependencies)
        componentDependencies.addAll(Preconditions.emptyOnNull( //TODO check the classes are indeed dagger components
                sharpComponentAnnotation.<List<? extends AnnotationValue>>argumentOrNull("dependencies"))
        );

        componentSharpDependencies.addAll(Preconditions.emptyOnNull( //TODO check the classes annotated as SharpComponents
                sharpComponentAnnotation.<List<? extends AnnotationValue>>argumentOrNull("sharpDependencies"))
        );

        componentProvides.addAll(Preconditions.emptyOnNull(
                sharpComponentAnnotation.<List<? extends AnnotationValue>>argumentOrNull("provides"))
        );

        componentProvidesNamed.addAll(ProvidesNamedUtils.getNamedProvides(
                Preconditions.emptyOnNull(
                        sharpComponentAnnotation.<List<? extends AnnotationValue>>argumentOrNull("providesNamed"))
                )
        );

    }

    public String getComponentName() {
        return String.format(Locale.UK, SharpEnvConstants.componentNameStringPattern, annotatedClass.getSimpleName());
    }

    public String getSharpScopeName() {
        return String.format(Locale.UK, SharpEnvConstants.scopeNameStringPattern, annotatedClass.getSimpleName());
    }

    public List<AnnotationValue> getComponentDependencies() {
        return new LinkedList<>(componentDependencies);
    }

    public List<AnnotationValue> getComponentSharpDependencies() {
        return componentSharpDependencies;
    }

    public List<AnnotationValue> getComponentModules() {
        return new LinkedList<>(componentModules);
    }

    public List<AnnotationValue> getComponentProvides() {
        return new LinkedList<>(componentProvides);
    }

    public List<NamedTypeMirror> getComponentProvidesNamed() {
        return componentProvidesNamed;
    }
}
