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
 * BlazingMQ binary wire protocol implementation.
 *
 * <p>This package contains the low-level protocol types for constructing and parsing BlazingMQ
 * binary events. It includes event builders ({@link
 * com.bloomberg.bmq.impl.infr.proto.PutEventBuilder}, {@link
 * com.bloomberg.bmq.impl.infr.proto.AckEventBuilder}, {@link
 * com.bloomberg.bmq.impl.infr.proto.PushEventBuilder}, {@link
 * com.bloomberg.bmq.impl.infr.proto.ConfirmEventBuilder}), message iterators for reading event
 * payloads, header types, message property handling, CRC32-C checksumming, and protocol constants
 * defined in {@link com.bloomberg.bmq.impl.infr.proto.Protocol}.
 *
 * <p><b>Note:</b> This package is internal to the SDK and is not part of the public API.
 */
package com.bloomberg.bmq.impl.infr.proto;
