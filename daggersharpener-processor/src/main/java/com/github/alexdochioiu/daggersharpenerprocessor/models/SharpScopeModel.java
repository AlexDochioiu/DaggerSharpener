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

package com.github.alexdochioiu.daggersharpenerprocessor.models;

import com.github.alexdochioiu.daggersharpenerprocessor.general.Preconditions;

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
        Preconditions.checkNotNull(scopeTypeMirror);
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
