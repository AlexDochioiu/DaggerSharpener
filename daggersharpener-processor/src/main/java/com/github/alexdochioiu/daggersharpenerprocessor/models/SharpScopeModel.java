package com.github.alexdochioiu.daggersharpenerprocessor.models;

import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 7/26/2018
 */
public class SharpScopeModel {
    private final TypeMirror scopeTypeMirror;
    private final boolean isSharpScoped;

    /**
     * Constructor used when the sharp component parent class has a custom scope defined
     * @param scopeTypeMirror the given scope
     */
    public SharpScopeModel(TypeMirror scopeTypeMirror) {
        this.scopeTypeMirror = scopeTypeMirror;
        isSharpScoped = false;
    }

    /**
     * Constructor used when the scope should be created for the generated component
     */
    public SharpScopeModel() {
        scopeTypeMirror = null;
        isSharpScoped = true;
    }

    /**
     *
     * @return <b>NULL</b> if it is sharp scoped or {@link TypeMirror} of the @interface scope otherwise
     */
    public TypeMirror getScopeTypeMirror() {
        return scopeTypeMirror;
    }

    /**
     *
     * @return whether this scope model is sharp scoped, in which case we need to come up with a scope ourselves
     */
    public boolean isSharpScoped() {
        return isSharpScoped;
    }
}
