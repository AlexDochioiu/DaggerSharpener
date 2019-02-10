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
package com.github.alexdochioiu.daggersharpenerprocessor;

import com.github.alexdochioiu.daggersharpenerprocessor.general.GeneralHelper;
import com.github.alexdochioiu.daggersharpenerprocessor.general.concrete.TypeClass;
import com.github.alexdochioiu.daggersharpenerprocessor.models.SharpComponentModel;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.SharpEnvConstants;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2.AnnotationUtils;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2.NamedAnnotationUtils;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.java.AnnotationValueUtils;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.java.NamedTypeMirror;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SharpProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotation = new LinkedHashSet<>();
        supportedAnnotation.add("com.github.alexdochioiu.daggersharpener.SharpComponent");

        return supportedAnnotation;
    }

    private ProcessingEnvironment processingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        this.processingEnvironment = processingEnvironment;
        MessagerWrapper.initInstance(processingEnvironment.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        final GeneralHelper helpers = new GeneralHelper(processingEnvironment, roundEnvironment);

        for (SharpComponentModel model : getComponents(helpers)) {
            createDaggerComponent(model);
        }

        return true;
    }

    private void createDaggerComponent(SharpComponentModel model) {
        TypeSpec.Builder generatedComponentBuilder = TypeSpec.interfaceBuilder(model.getComponentName())
                .addAnnotation(AnnotationUtils.getDaggerComponentAnnotation(
                        processingEnvironment,
                        model.getComponentDependencies(),
                        model.getComponentSharpDependencies(),
                        model.getComponentModules()))
                .addModifiers(Modifier.PUBLIC);

        if (model.scope.isSharpScoped()) {
            createDaggerSharpScopeFile(model);

            generatedComponentBuilder.addAnnotation(ClassName.get(model.annotatedClass.getPackage(), model.getSharpScopeName()));
        } else {
            ClassName className = (ClassName) ClassName.get(model.scope.getScopeTypeMirror());
            generatedComponentBuilder.addAnnotation(className);
        }

        final ParameterSpec param = ParameterSpec.builder(model.annotatedClass.getJavaPoetClassName(), "thisClass").build();

        generatedComponentBuilder.addMethod(
                MethodSpec.methodBuilder("inject")
                        .returns(model.annotatedClass.getJavaPoetClassName())
                        .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                        .addParameter(param)
                        .build()
        );

        for (final AnnotationValue providedClass : model.getComponentProvides()) {
            generatedComponentBuilder.addMethod(
                    MethodSpec.methodBuilder(String.format("provide%s", AnnotationValueUtils.getSimpleName(providedClass)))
                            .returns(TypeName.get(processingEnvironment.getElementUtils().getTypeElement(AnnotationValueUtils.getFullName(providedClass)).asType()))
                            .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                            .build()
            );
        }

        for (NamedTypeMirror providedNamesClass : model.getComponentProvidesNamed()) {
            ClassName className = (ClassName) ClassName.get(providedNamesClass.getTypeMirror());
            generatedComponentBuilder.addMethod(
                    MethodSpec.methodBuilder(String.format("provide%s_%s", className.simpleName(), providedNamesClass.getName()))
                            .addAnnotation(NamedAnnotationUtils.getNamedAnnotation(providedNamesClass.getName()))
                            .returns(className)
                            .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                            .build()
            );
        }

        try {
            JavaFile.builder(model.annotatedClass.getPackage(), generatedComponentBuilder.build())
                    .addFileComment("Generated by DaggerSharpener: https://github.com/AlexDochioiu/DaggerSharpener")
                    .build()
                    .writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
            MessagerWrapper.logError(String.format("Could not generate component '%s'", model.getComponentName()));
        }
    }

    /**
     * Finds all the SharpComponent annotated classes and generates models for their associated
     * dagger components. Those models will then be used to generated the files.
     *
     * @return {@link List} of all {@link SharpComponentModel}
     */
    private List<SharpComponentModel> getComponents(GeneralHelper helper) {
        final List<SharpComponentModel> componentModels = new LinkedList<>();

        // get all the classes annotated as SharpComponent
        final Set<? extends Element> annotatedSharpComponent =
                helper.getElementsAnnotatedWith(
                        SharpEnvConstants.containedPackage,
                        SharpEnvConstants.sharpComponentClassName
                );

        // if there's no classes annotated, return our empty list
        if (annotatedSharpComponent == null || annotatedSharpComponent.isEmpty()) {
            return componentModels;
        }

        for (final Element element : annotatedSharpComponent) {
            final TypeClass sharpComponentTypeClass = helper.makeTypeClass(element);

            final SharpComponentModel model = new SharpComponentModel(sharpComponentTypeClass);

            componentModels.add(model);
        }

        return componentModels;
    }

    /**
     * <b>NOTE:</b> this assumes we already checked and a scope should be created for this component.
     *
     * @param model the {@link SharpComponentModel} requiring a scope
     */
    private void createDaggerSharpScopeFile(SharpComponentModel model) {
        AnnotationSpec runtimeRetention = AnnotationSpec.builder(ClassName.get("java.lang.annotation", "Retention"))
                .addMember("value", CodeBlock.builder().add("$T.RUNTIME", ClassName.get("java.lang.annotation", "RetentionPolicy")).build())
                .build();

        TypeSpec generatedScopeBuilder = TypeSpec.annotationBuilder(model.getSharpScopeName())
                .addAnnotation(ClassName.get("javax.inject", "Scope"))
                .addAnnotation(runtimeRetention)
                .addModifiers(Modifier.PUBLIC)
                .build();

        try {
            JavaFile.builder(model.annotatedClass.getPackage() , generatedScopeBuilder)
                    .addFileComment("Generated by DaggerSharpener")
                    .build()
                    .writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
            MessagerWrapper.logError(String.format("Could not generate scope '%s' for component '%s'", model.getSharpScopeName(), model.getComponentName()));
        }

        // return generatedScopeBuilder;
    }
}
