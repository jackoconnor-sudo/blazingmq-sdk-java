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
 * Control message definitions for the BlazingMQ wire protocol.
 *
 * <p>This package contains the data classes representing JSON-encoded control messages exchanged
 * during broker negotiation and queue management. Key types include {@link
 * com.bloomberg.bmq.impl.infr.msg.ClientIdentity} for session identification, {@link
 * com.bloomberg.bmq.impl.infr.msg.OpenQueue} and {@link
 * com.bloomberg.bmq.impl.infr.msg.CloseQueue} for queue lifecycle operations, {@link
 * com.bloomberg.bmq.impl.infr.msg.ConfigureStream} for stream configuration, and {@link
 * com.bloomberg.bmq.impl.infr.msg.Status}/{@link com.bloomberg.bmq.impl.infr.msg.StatusCategory}
 * for response status reporting.
 *
 * <p><b>Note:</b> This package is internal to the SDK and is not part of the public API.
 */
package com.bloomberg.bmq.impl.infr.msg;
