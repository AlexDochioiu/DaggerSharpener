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

package com.github.alexdochioiu.daggersharpenerprocessor.general.abstraction;

import com.github.alexdochioiu.daggersharpenerprocessor.general.Preconditions;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

/**
 * Created by Alexandru Iustin Dochioiu on 03-Feb-19
 */
public class BaseProcessorHelper {
    protected final ProcessingEnvironment processingEnvironment;
    protected final RoundEnvironment roundEnvironment;

    public BaseProcessorHelper(
            ProcessingEnvironment processingEnvironment,
            RoundEnvironment roundEnvironment) {
        this.processingEnvironment = Preconditions.checkNotNull(processingEnvironment);;
        this.roundEnvironment = Preconditions.checkNotNull(roundEnvironment);
    }
}
