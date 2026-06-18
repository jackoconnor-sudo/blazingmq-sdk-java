/*
 * Copyright 2022 Bloomberg Finance L.P.
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

/**
 * Subscription expression validation for the BlazingMQ Java SDK.
 *
 * <p>This package provides a JFlex-based lexical analyzer for validating BlazingMQ subscription
 * expressions. Key types include {@link
 * com.bloomberg.bmq.impl.infr.util.expressionvalidator.ExpressionValidator} for performing the
 * validation, {@link com.bloomberg.bmq.impl.infr.util.expressionvalidator.Token} for tokenized
 * expression elements, and {@link
 * com.bloomberg.bmq.impl.infr.util.expressionvalidator.ValidationResult} for reporting
 * validation outcomes.
 *
 * <p><b>Note:</b> This package is internal to the SDK and is not part of the public API.
 */
package com.bloomberg.bmq.impl.infr.util.expressionvalidator;
