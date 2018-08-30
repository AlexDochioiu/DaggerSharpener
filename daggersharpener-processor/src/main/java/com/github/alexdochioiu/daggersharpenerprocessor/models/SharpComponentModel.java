package com.github.alexdochioiu.daggersharpenerprocessor.models;

import com.github.alexdochioiu.daggersharpenerprocessor.MessagerWrapper;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.ScopeClassUtils;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.SharpEnvConstants;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.SharpenerAnnotationUtils;
import com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2.ScopeUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
@SuppressWarnings("WeakerAccess")
public class SharpComponentModel {
    public final TypeName injectedClass;

    public final String packageString;
    public final String className;
    public final SharpScopeModel scope;
    public final List<TypeMirror> classesProvided = new LinkedList<>();

    public final List<TypeMirror> componentDependencies = new LinkedList<>();
    public final List<TypeMirror> componentModules = new LinkedList<>();

    /**
     * Constructor
     *
     * @param poetClassName   non-null class name
     * @param typeElement non-null type element for a class
     */
    public SharpComponentModel(final ClassName poetClassName, final TypeElement typeElement, final ProcessingEnvironment processingEnvironment) {
        injectedClass = TypeName.get(typeElement.asType());
        packageString = poetClassName.packageName();
        this.className = poetClassName.simpleName();

        SharpScopeModel newSharpScopeModel = null;


        final TypeElement sharpComponentAnnotation = SharpenerAnnotationUtils.getSharpComponentAnnotation();

        for (AnnotationMirror annotationMirror : typeElement.getAnnotationMirrors()) {
            // iterate all annotations applied to class

            if (processingEnvironment.getTypeUtils().isSameType(annotationMirror.getAnnotationType(), sharpComponentAnnotation.asType())) {
                // found the @SharpComponent annotation

                MessagerWrapper.logWarning("%s is sharp component", poetClassName.simpleName());

                // Get the ExecutableElement:AnnotationValue pairs from the annotation element
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();

                boolean foundScopeTag = false;

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                        : elementValues.entrySet()) {
                    String key = entry.getKey().getSimpleName().toString();
                    Object value = entry.getValue().getValue();
                    MessagerWrapper.logWarning(">> key: %s", key);
                    switch (key) {
                        case "scope":
                            TypeMirror classType = (TypeMirror) value;
                            newSharpScopeModel = ScopeClassUtils.getSharpScopeModel(classType, processingEnvironment);

                            MessagerWrapper.logWarning(">> scope: %s\n", classType);
                            foundScopeTag = true;
                            break;
                            //TODO rest of them
                        case "stringValue":
                            String strVal = (String) value;
                            System.out.printf(">> stringValue: %s\n", strVal);
                            break;
                    }
                }

                if (!foundScopeTag) {
                    // if the tag was not explicitly defined, we use the default value (SharpScope.class)
                    newSharpScopeModel = new SharpScopeModel();
                }

                break;
            }
        }

        this.scope = newSharpScopeModel;
    }

    public String getComponentName() {
        return String.format(Locale.UK, SharpEnvConstants.componentNameStringPattern, className);
    }

    public String getSharpScopeName() {
        return String.format(Locale.UK, SharpEnvConstants.scopeNameStringPattern, className);
    }
}
