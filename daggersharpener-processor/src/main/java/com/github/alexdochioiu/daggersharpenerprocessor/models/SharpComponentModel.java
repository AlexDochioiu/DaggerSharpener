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

    private final List<AnnotationValue> componentDependencies = new LinkedList<>();
    private final List<AnnotationValue> componentSharpDependencies = new LinkedList<>();
    private final List<AnnotationValue> componentModules = new LinkedList<>();
    private final List<AnnotationValue> componentProvides = new LinkedList<>();


    /**
     * Constructor
     *
     * @param poetClassName non-null class name
     * @param typeElement   non-null type element for a class
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
                        case "modules":
                            @SuppressWarnings("unchecked") List<? extends AnnotationValue> annotaionModules
                                    = (List<? extends AnnotationValue>) value;

                            componentModules.addAll(annotaionModules);

                            for (AnnotationValue annotationValue : annotaionModules) {
                                MessagerWrapper.logWarning(">> module: %s", annotationValue);
                            }

                            break;
                        case "sharpDependencies":
                            //TODO check the classes annotated as SharpComponents
                            @SuppressWarnings("unchecked") List<? extends AnnotationValue> annotationSharpDependencies
                                    = (List<? extends AnnotationValue>) value;

                            componentSharpDependencies.addAll(annotationSharpDependencies);

                            for (AnnotationValue annotationValue : annotationSharpDependencies) {
                                MessagerWrapper.logWarning(">> sharpDependencies: %s", annotationValue);
                            }
                            break;
                        case "dependencies":
                            // THIS IS FOR MANUALLY CREATED DEPENDENCIES (not sharp dependencies)
                            //TODO check the classes are indeed dagger components
                            @SuppressWarnings("unchecked") List<? extends AnnotationValue> annotationDependencies
                                    = (List<? extends AnnotationValue>) value;

                            componentDependencies.addAll(annotationDependencies);

                            for (AnnotationValue annotationValue : annotationDependencies) {
                                MessagerWrapper.logWarning(">> dependency: %s", annotationValue);
                            }
                            break;
                        case "provides":
                            @SuppressWarnings("unchecked") List<? extends AnnotationValue> annotationProvides
                                    = (List<? extends AnnotationValue>) value;

                            componentProvides.addAll(annotationProvides);

                            for (AnnotationValue annotationValue : annotationProvides) {
                                MessagerWrapper.logWarning(">> provides: %s", annotationValue);
                            }
                            break;
                        default:
                            MessagerWrapper.logWarning("Unknown parameter '%s' on SharpComponent", key);
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
}
