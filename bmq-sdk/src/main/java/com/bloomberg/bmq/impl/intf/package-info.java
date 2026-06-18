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
 * Internal interfaces for the BlazingMQ Java SDK implementation.
 *
 * <p>This package defines the internal contracts used between SDK components, including {@link
 * com.bloomberg.bmq.impl.intf.BrokerConnection} for broker communication, {@link
 * com.bloomberg.bmq.impl.intf.BrokerConnectionFSM} for connection state machine transitions, {@link
 * com.bloomberg.bmq.impl.intf.QueueHandle} for queue operations, and {@link
 * com.bloomberg.bmq.impl.intf.SessionEventHandler} for handling wire protocol I/O events.
 *
 * <p><b>Note:</b> This package is internal to the SDK and is not part of the public API.
 */
package com.bloomberg.bmq.impl.intf;
