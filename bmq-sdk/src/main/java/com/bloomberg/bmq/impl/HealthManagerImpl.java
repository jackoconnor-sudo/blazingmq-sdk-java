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
package com.bloomberg.bmq.impl;

import java.lang.invoke.MethodHandles;
import java.util.function.BooleanSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages host health state tracking for queue suspend/resume operations.
 *
 * <p>This class tracks the number of pending host health requests and coordinates the transition
 * between healthy and unhealthy states. It is used by queue control strategies to determine when
 * all queues have been suspended or resumed and a corresponding session event should be emitted.
 */
public final class HealthManagerImpl implements BrokerSession.HealthManager {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BooleanSupplier sessionExecutorCheck;
    private final BooleanSupplier hostHealthyCheck;
    private final Runnable stableHealthyCallback;

    private int numPendingHostHealthRequests = 0;

    /**
     * Creates a new HealthManagerImpl.
     *
     * @param sessionExecutorCheck returns true if the current thread is the session executor
     * @param hostHealthyCheck returns true if the host is currently healthy
     * @param stableHealthyCallback invoked when stable healthy state is reached (no pending
     *     requests and host is healthy)
     */
    public HealthManagerImpl(
            BooleanSupplier sessionExecutorCheck,
            BooleanSupplier hostHealthyCheck,
            Runnable stableHealthyCallback) {
        this.sessionExecutorCheck = sessionExecutorCheck;
        this.hostHealthyCheck = hostHealthyCheck;
        this.stableHealthyCallback = stableHealthyCallback;
    }

    @Override
    public void onHealthRequest() {
        assert sessionExecutorCheck.getAsBoolean();

        ++numPendingHostHealthRequests;
        logger.debug(
                "Incremented num of pending host health requests to {}",
                numPendingHostHealthRequests);
    }

    @Override
    public void onHealthResponse() {
        assert sessionExecutorCheck.getAsBoolean();

        if (numPendingHostHealthRequests <= 0) {
            logger.warn("Attempt to decrement num of pending host health requests below zero");
            return;
        }

        --numPendingHostHealthRequests;
        logger.debug(
                "Decremented num of pending host health requests to {}",
                numPendingHostHealthRequests);
    }

    @Override
    public boolean isHostHealthy() {
        assert sessionExecutorCheck.getAsBoolean();
        return hostHealthyCheck.getAsBoolean();
    }

    @Override
    public void checkHostIsStableHealthy() {
        assert sessionExecutorCheck.getAsBoolean();
        stableHealthyCallback.run();
    }

    public int getNumPendingHostHealthRequests() {
        return numPendingHostHealthRequests;
    }

    public void incrementPendingRequests() {
        ++numPendingHostHealthRequests;
        logger.debug(
                "Incremented (guard) num of pending host health requests to {}",
                numPendingHostHealthRequests);
    }

    public void decrementPendingRequests() {
        --numPendingHostHealthRequests;
        logger.debug(
                "Decremented (guard) num of pending host health requests to {}",
                numPendingHostHealthRequests);
    }
}
