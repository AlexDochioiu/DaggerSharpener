package com.github.alexdochioiu.daggersharpenerprocessor.models;

import com.github.alexdochioiu.daggersharpenerprocessor.utils.dagger2.ScopeUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
@SuppressWarnings("WeakerAccess")
public class SharpComponentModel {
    public final TypeName injectedClass;

    public final String packageString;
    public final String componentName;
    public final SharpScopeModel scope;
    public final List<TypeMirror> classesProvided = new LinkedList<>();

    public final List<TypeMirror> componentDependencies = new LinkedList<>();
    public final List<TypeMirror> componentModules = new LinkedList<>();

    /**
     * Constructor
     *
     * @param className   non-null class name
     * @param typeElement non-null type element for a class
     */
    public SharpComponentModel(final ClassName className, final TypeElement typeElement) {
        injectedClass = TypeName.get(typeElement.asType());
        packageString = className.packageName();
        componentName = String.format(Locale.UK, "Sharp%sComponent", className.simpleName());
        scope = getScope(className, typeElement);
    }

    /**
     *
     * @return the scope if any or null if it has no scope
     */
    private SharpScopeModel getScope(final ClassName className, final TypeElement typeElement) {
        final TypeElement scopeElement = ScopeUtils.getScopeElement();

        //TODO this should verify it the class has an annotation which is a scope
        return new SharpScopeModel();
    }
}
