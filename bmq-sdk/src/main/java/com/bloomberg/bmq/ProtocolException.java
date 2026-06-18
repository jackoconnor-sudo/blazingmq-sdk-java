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
package com.bloomberg.bmq;

/**
 * A {@link BMQException} which is raised when a protocol or serialization failure occurs during
 * BlazingMQ message processing.
 */
@SuppressWarnings("serial")
public class ProtocolException extends BMQException {

    public ProtocolException(Exception ex) {
        super(ex);
    }

    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolException(Exception ex, ResultCodes.GenericCode code) {
        super(ex, code);
    }

    public ProtocolException(String message, ResultCodes.GenericCode code) {
        super(message, code);
    }

    public ProtocolException(String message, Throwable cause, ResultCodes.GenericCode code) {
        super(message, cause, code);
    }
}
