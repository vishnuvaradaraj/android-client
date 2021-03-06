/*
 * Copyright 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.parabay.client.ui.validation.client.validator;

import com.parabay.client.ui.validation.client.Subject;
import com.parabay.client.ui.validation.client.validator.DependentSubjectValidator;

/**
 * Represents the '<' relationship.
 */
public class LessThanValidator extends DependentSubjectValidator {

  public LessThanValidator(Subject dependent) {
    super(dependent);
  }

  public String handleError(String dependentLabel, Subject target) {
    return getMessages().lessThan(target.getLabel(), target.getValue(),
        dependentLabel);
  }

  public boolean isValid(Object dependentValue, Object targetValue) {
    return ((Comparable) dependentValue).compareTo(targetValue) < 0;
  }
}