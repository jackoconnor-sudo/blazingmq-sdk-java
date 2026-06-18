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

import com.bloomberg.bmq.ResultCodes.GenericResult;
import com.bloomberg.bmq.impl.BrokerSession;
import com.bloomberg.bmq.impl.QueueId;
import com.bloomberg.bmq.impl.events.AckMessageEvent;
import com.bloomberg.bmq.impl.events.BrokerSessionEvent;
import com.bloomberg.bmq.impl.events.BrokerSessionEventHandler;
import com.bloomberg.bmq.impl.events.Event;
import com.bloomberg.bmq.impl.events.PushMessageEvent;
import com.bloomberg.bmq.impl.events.QueueControlEvent;
import com.bloomberg.bmq.impl.infr.proto.AckMessageImpl;
import com.bloomberg.bmq.impl.infr.proto.PushMessageImpl;
import com.bloomberg.bmq.impl.infr.util.Argument;
import com.bloomberg.bmq.impl.intf.QueueHandle;
import java.lang.invoke.MethodHandles;
import javax.annotation.concurrent.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles dispatching of broker session events and message events to the appropriate handlers.
 *
 * <p>This class encapsulates the event dispatching logic that translates internal broker events into
 * public session events and routes message events to the correct queue handles.
 */
final class SessionEventDispatcher {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AbstractSession session;
    private final SessionEventHandler sessionEventHandler;
    private final BrokerSession brokerSession;
    private final BrokerSessionEventHandler brokerEventHandler;
    private final com.bloomberg.bmq.impl.events.EventHandler eventHandler;

    SessionEventDispatcher(
            AbstractSession session,
            SessionEventHandler sessionEventHandler,
            BrokerSession brokerSession) {
        this.session = Argument.expectNonNull(session, "session");
        this.sessionEventHandler =
                Argument.expectNonNull(sessionEventHandler, "sessionEventHandler");
        this.brokerSession = Argument.expectNonNull(brokerSession, "brokerSession");
        this.brokerEventHandler = new BrokerSessionEventDispatcherImpl();
        this.eventHandler = new EventDispatcherImpl();
    }

    BrokerSessionEventHandler getBrokerEventHandler() {
        return brokerEventHandler;
    }

    com.bloomberg.bmq.impl.events.EventHandler getEventHandler() {
        return eventHandler;
    }

    private static void dispatchSessionEvent(SessionEventAdapterBase event) {
        event.dispatch();
    }

    private class EventAdapter implements com.bloomberg.bmq.Event {
        @Override
        public AbstractSession session() {
            return SessionEventDispatcher.this.session;
        }
    }

    @Immutable
    abstract class SessionEventAdapterBase extends EventAdapter implements SessionEvent {
        private final long creationTime;
        private final String errorDescription;

        protected SessionEventAdapterBase(BrokerSessionEvent event) {
            Argument.expectNonNull(event, "event");
            creationTime = event.getCreationTime();
            errorDescription = event.getErrorDescription();
        }

        public abstract void dispatch();

        @Override
        public String toString() {
            return "Event [Type: "
                    + this.type()
                    + " Creation time in broker session: "
                    + creationTime
                    + " Description: "
                    + errorDescription
                    + "]";
        }
    }

    @Immutable
    private class UnknownEvent extends SessionEventAdapterBase {
        UnknownEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleSessionEvent(this);
        }

        @Override
        public Type type() {
            return Type.UNKNOWN_SESSION_EVENT;
        }
    }

    @Immutable
    private class ConnectionLostEvent extends SessionEventAdapterBase
            implements SessionEvent.ConnectionLost {
        ConnectionLostEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleConnectionLostSessionEvent(this);
        }
    }

    @Immutable
    private class ReconnectedEvent extends SessionEventAdapterBase
            implements SessionEvent.Reconnected {
        ReconnectedEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleReconnectedSessionEvent(this);
        }
    }

    @Immutable
    private class StateRestoredEvent extends SessionEventAdapterBase
            implements SessionEvent.StateRestored {
        StateRestoredEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleStateRestoredSessionEvent(this);
        }
    }

    @Immutable
    private class SlowConsumerHighWatermarkEvent extends SessionEventAdapterBase
            implements SessionEvent.SlowConsumerHighWatermark {
        SlowConsumerHighWatermarkEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleSlowConsumerHighWatermarkEvent(this);
        }
    }

    @Immutable
    private class SlowConsumerNormalEvent extends SessionEventAdapterBase
            implements SessionEvent.SlowConsumerNormal {
        SlowConsumerNormalEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleSlowConsumerNormalEvent(this);
        }
    }

    @Immutable
    private class StartStatusEvent extends SessionEventAdapterBase
            implements SessionEvent.StartStatus {
        private final GenericResult result;

        StartStatusEvent(BrokerSessionEvent event, GenericResult res) {
            super(event);
            result = Argument.expectNonNull(res, "res");
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleStartStatusSessionEvent(this);
        }

        @Override
        public GenericResult result() {
            return result;
        }
    }

    @Immutable
    private class StopStatusEvent extends SessionEventAdapterBase
            implements SessionEvent.StopStatus {
        private final GenericResult result;

        StopStatusEvent(BrokerSessionEvent event, GenericResult res) {
            super(event);
            result = Argument.expectNonNull(res, "res");
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleStopStatusSessionEvent(this);
        }

        @Override
        public GenericResult result() {
            return result;
        }
    }

    @Immutable
    private class HostUnhealthyEvent extends SessionEventAdapterBase
            implements SessionEvent.HostUnhealthy {
        HostUnhealthyEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleHostUnhealthySessionEvent(this);
        }
    }

    @Immutable
    private class HostHealthRestoredEvent extends SessionEventAdapterBase
            implements SessionEvent.HostHealthRestored {
        HostHealthRestoredEvent(BrokerSessionEvent event) {
            super(event);
        }

        @Override
        public void dispatch() {
            sessionEventHandler.handleHostHealthRestoredSessionEvent(this);
        }
    }

    @Immutable
    private class BrokerSessionEventDispatcherImpl implements BrokerSessionEventHandler {
        @Override
        public void handleConnected(BrokerSessionEvent event) {
            dispatchSessionEvent(new StartStatusEvent(event, GenericResult.SUCCESS));
        }

        @Override
        public void handleDisconnected(BrokerSessionEvent event) {
            dispatchSessionEvent(new StopStatusEvent(event, GenericResult.SUCCESS));
        }

        @Override
        public void handleConnectionLost(BrokerSessionEvent event) {
            dispatchSessionEvent(new ConnectionLostEvent(event));
        }

        @Override
        public void handleReconnected(BrokerSessionEvent event) {
            dispatchSessionEvent(new ReconnectedEvent(event));
        }

        @Override
        public void handleStateRestored(BrokerSessionEvent event) {
            dispatchSessionEvent(new StateRestoredEvent(event));
        }

        @Override
        public void handleConnectionTimeout(BrokerSessionEvent event) {
            dispatchSessionEvent(new StartStatusEvent(event, GenericResult.TIMEOUT));
        }

        @Override
        public void handleSlowConsumerNormal(BrokerSessionEvent event) {
            dispatchSessionEvent(new SlowConsumerNormalEvent(event));
        }

        @Override
        public void handleSlowConsumerHighWatermark(BrokerSessionEvent event) {
            dispatchSessionEvent(new SlowConsumerHighWatermarkEvent(event));
        }

        @Override
        public void handleConnectionInProgress(BrokerSessionEvent event) {
            dispatchSessionEvent(new StartStatusEvent(event, GenericResult.NOT_SUPPORTED));
        }

        @Override
        public void handleDisconnectionTimeout(BrokerSessionEvent event) {
            dispatchSessionEvent(new StopStatusEvent(event, GenericResult.TIMEOUT));
        }

        @Override
        public void handleDisconnectionInProgress(BrokerSessionEvent event) {
            dispatchSessionEvent(new StopStatusEvent(event, GenericResult.NOT_SUPPORTED));
        }

        @Override
        public void handleHostUnhealthy(BrokerSessionEvent event) {
            dispatchSessionEvent(new HostUnhealthyEvent(event));
        }

        @Override
        public void handleHostHealthRestored(BrokerSessionEvent event) {
            dispatchSessionEvent(new HostHealthRestoredEvent(event));
        }

        @Override
        public void handleError(BrokerSessionEvent event) {
            dispatchSessionEvent(new UnknownEvent(event));
        }

        @Override
        public void handleCancelled(BrokerSessionEvent event) {
            dispatchSessionEvent(new StartStatusEvent(event, GenericResult.CANCELED));
        }
    }

    @Immutable
    private class EventDispatcherImpl implements com.bloomberg.bmq.impl.events.EventHandler {

        @Override
        public void handleEvent(Event event) {
            logger.error("Unexpected event caught: {}", event);
        }

        @Override
        public void handlePushMessageEvent(PushMessageEvent ev) {
            PushMessageImpl msg = ev.rawMessage();
            Integer[] subQueueIds = msg.subQueueIds();
            for (Integer subQId : subQueueIds) {
                QueueHandle queue = brokerSession.lookupQueue(subQId);
                if (queue != null) {
                    queue.handlePushMessage(msg);
                } else {
                    logger.warn("Received PUSH message for unknown queue: {}", msg);
                }
            }
        }

        @Override
        public void handleAckMessageEvent(AckMessageEvent ev) {
            AckMessageImpl msg = ev.rawMessage();
            QueueId qid = QueueId.createInstance(msg.queueId(), 0);
            QueueHandle queue = brokerSession.lookupQueue(qid);
            if (queue != null) {
                queue.handleAckMessage(msg);
            } else {
                logger.warn("Received ACK message for unknown queue: {}", msg);
            }
        }

        @Override
        public void handleQueueEvent(QueueControlEvent ev) {
            QueueHandle queue = ev.getQueue();
            if (queue == null)
                throw new RuntimeException("Failure: Queue is null. Ev: " + ev.toString());
            queue.handleQueueEvent(ev);
        }

        @Override
        public void handleBrokerSessionEvent(BrokerSessionEvent event) {
            event.dispatch(brokerEventHandler);
        }
    }
}
