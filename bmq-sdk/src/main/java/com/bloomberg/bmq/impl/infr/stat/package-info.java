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
 * Statistics collection and reporting for the BlazingMQ Java SDK.
 *
 * <p>This package provides classes for tracking SDK performance metrics, including {@link
 * com.bloomberg.bmq.impl.infr.stat.EventQueueStats} for event queue statistics, {@link
 * com.bloomberg.bmq.impl.infr.stat.EventsStats} for event processing metrics, {@link
 * com.bloomberg.bmq.impl.infr.stat.QueuesStats} for per-queue statistics, and {@link
 * com.bloomberg.bmq.impl.infr.stat.Stats} as the central statistics aggregator.
 *
 * <p><b>Note:</b> This package is internal to the SDK and is not part of the public API.
 */
package com.bloomberg.bmq.impl.infr.stat;
