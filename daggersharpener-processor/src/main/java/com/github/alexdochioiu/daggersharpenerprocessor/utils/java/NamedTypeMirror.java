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
package com.github.alexdochioiu.daggersharpenerprocessor.utils.java;

import javax.lang.model.type.TypeMirror;

/**
 * Created by Alexandru Iustin Dochioiu on 12-Jan-19
 */
public class NamedTypeMirror {
    private final String name;
    private final TypeMirror typeMirror;

    private NamedTypeMirror(String name, TypeMirror typeMirror) {
        this.name = name;
        this.typeMirror = typeMirror;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static class Builder {
        private String name = null;
        private TypeMirror typeMirror = null;

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withTypeMirror(final TypeMirror typeMirror) {
            this.typeMirror = typeMirror;
            return this;
        }

        public boolean hasNameAndTypeMirror() {
            return name != null && typeMirror != null;
        }

        public NamedTypeMirror build() {
            return new NamedTypeMirror(name, typeMirror);
        }
    }
}
