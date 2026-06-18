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
 * Network infrastructure for the BlazingMQ Java SDK.
 *
 * <p>This package provides the Netty-based TCP networking layer, including {@link
 * com.bloomberg.bmq.impl.infr.net.NettyTcpConnection} and {@link
 * com.bloomberg.bmq.impl.infr.net.NettyTcpConnectionFactory} for establishing and managing TCP
 * connections to the BlazingMQ broker, along with {@link
 * com.bloomberg.bmq.impl.infr.net.ConnectionOptions} for connection configuration.
 *
 * <p><b>Note:</b> This package is internal to the SDK and is not part of the public API.
 */
package com.bloomberg.bmq.impl.infr.net;
