package com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.SharpEnvConstants;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
public class AnnotationUtils {
    private static TypeElement daggerComponentTypeElement;

    public static boolean init(ProcessingEnvironment processingEnvironment) {
        daggerComponentTypeElement = processingEnvironment
                .getElementUtils()
                .getTypeElement("dagger.Component");

        if (daggerComponentTypeElement == null) {
            MessagerWrapper.logWarning("AnnotationUtils: Missing dagger2 dependency. Please add it to gradle!");
            //return false;
        }
        return true;
    }

    public static AnnotationSpec getDaggerComponentAnnotation(
            ProcessingEnvironment processingEnvironment,
            List<AnnotationValue> dependencies,
            List<AnnotationValue> sharpDependencies,
            List<AnnotationValue> modules
    ) {
        AnnotationSpec.Builder daggerComponentBuilder = AnnotationSpec.builder(ClassName.get(daggerComponentTypeElement));

        if (dependencies == null) {
            dependencies = new LinkedList<>();
        }
        if (sharpDependencies == null) {
            sharpDependencies = new LinkedList<>();
        }
        if (modules == null) {
            modules = new LinkedList<>();
        }

        if (modules.size() > 0) {
            daggerComponentBuilder.addMember("modules", getAnnotationValuesAsCodeBlock(processingEnvironment, modules));
        }
        if (dependencies.size() > 0 || sharpDependencies.size() > 0) {
            daggerComponentBuilder.addMember("dependencies", getDependenciesAsString(sharpDependencies, dependencies));
        }

        return daggerComponentBuilder.build();
    }

    /**
     * @return a CodeBlock something like: "{A.class, B.class}" with the included packages to have visibility for the classes
     */
    private static CodeBlock getAnnotationValuesAsCodeBlock(
            ProcessingEnvironment processingEnvironment,
            List<AnnotationValue> annotationValues
    ) {
        final StringBuilder stringBuilderCodeBlock = new StringBuilder();
        final List<TypeElement> classes = new LinkedList<>();

        stringBuilderCodeBlock.append("{");
        for (AnnotationValue annotationValue : annotationValues) {
            String moduleClassName = annotationValue.toString(); // includes .class at the end
            moduleClassName = moduleClassName.substring(0, moduleClassName.length() - 6); // remove .class at the end
            classes.add(processingEnvironment.getElementUtils().getTypeElement(moduleClassName));

            stringBuilderCodeBlock.append("$T.class, ");
        }

        stringBuilderCodeBlock.setLength(stringBuilderCodeBlock.length() - 2);
        stringBuilderCodeBlock.append("}");

        final CodeBlock.Builder moduleBlock = CodeBlock.builder().add(stringBuilderCodeBlock.toString(), classes.toArray());
        return moduleBlock.build();
    }

    /**
     * Returns a CodeBlock something like: "{A.class, B.class}" with the included packages to have visibility for the classes
     */
    private static String getDependenciesAsString(
            List<AnnotationValue> sharpDependencies,
            List<AnnotationValue> daggerDependencies
    ) {
        final StringBuilder stringBuilderCodeBlock = new StringBuilder();

        stringBuilderCodeBlock.append("{");
        for (AnnotationValue annotationValue : sharpDependencies) {
            String moduleClassName = annotationValue.toString(); // includes .class at the end
            moduleClassName = moduleClassName.substring(0, moduleClassName.length() - 6); // remove .class at the end

            // get the simple name of the sharp component to be generated
            final String sharpComponentSimpleName = String.format(SharpEnvConstants.componentNameStringPattern, moduleClassName.substring(moduleClassName.lastIndexOf(".") + 1));

            moduleClassName = moduleClassName.substring(0, moduleClassName.lastIndexOf(".") + 1) + sharpComponentSimpleName;

            stringBuilderCodeBlock.append(moduleClassName).append(".class, ");
        }

        for (AnnotationValue annotationValue : daggerDependencies) {
            String moduleClassName = annotationValue.toString(); // includes .class at the end
            moduleClassName = moduleClassName.substring(0, moduleClassName.length() - 6); // remove .class at the end

            stringBuilderCodeBlock.append(moduleClassName).append(", ");
        }

        stringBuilderCodeBlock.setLength(stringBuilderCodeBlock.length() - 2);
        stringBuilderCodeBlock.append("}");

        return stringBuilderCodeBlock.toString();
    }
}
