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
 * Internal implementation of the BlazingMQ Java SDK.
 *
 * <p>This package contains the core implementation classes including {@link
 * com.bloomberg.bmq.impl.BrokerSession} for managing the broker connection lifecycle, {@link
 * com.bloomberg.bmq.impl.QueueImpl} for queue operations, {@link com.bloomberg.bmq.impl.PutPoster}
 * for batching and sending PUT messages, {@link com.bloomberg.bmq.impl.QueueStateManager} for
 * tracking queue states, and {@link com.bloomberg.bmq.impl.InboundEventBuffer} for buffering
 * incoming events with watermark-based flow control.
 *
 * <p><b>Note:</b> This package is internal to the SDK and is not part of the public API.
 */
package com.bloomberg.bmq.impl;
