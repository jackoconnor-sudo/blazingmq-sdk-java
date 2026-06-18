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
 * A {@link BMQException} which is raised when a connection-related failure occurs during broker
 * session operations.
 */
@SuppressWarnings("serial")
public class BrokerConnectionException extends BMQException {

    public BrokerConnectionException(Exception ex) {
        super(ex);
    }

    public BrokerConnectionException(String message) {
        super(message);
    }

    public BrokerConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrokerConnectionException(Exception ex, ResultCodes.GenericCode code) {
        super(ex, code);
    }

    public BrokerConnectionException(String message, ResultCodes.GenericCode code) {
        super(message, code);
    }

    public BrokerConnectionException(String message, Throwable cause, ResultCodes.GenericCode code) {
        super(message, cause, code);
    }
}
