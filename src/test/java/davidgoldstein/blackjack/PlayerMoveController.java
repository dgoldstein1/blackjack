package davidgoldstein.blackjack;

import davidgoldstein.blackjack.BlackjackApplication;
import davidgoldstein.blackjack.api.PlayerEventRequest;
import davidgoldstein.blackjack.api.PlayerEventRequestTest;
import davidgoldstein.blackjack.api.PlayerEventResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerMoveController {

    @LocalServerPort
    private Integer port;

    private WebSocketStompClient webSocketStompClient;

    @BeforeEach
    public void setup() {
        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void verifyGreetingIsReceived() throws Exception {
        ArrayBlockingQueue<PlayerEventResponse> blockingQueue = new ArrayBlockingQueue(1);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSession session = webSocketStompClient
                .connect(getWsPath(), new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);

        session.subscribe("/topic/greetings", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return PlayerEventRequest.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                blockingQueue.add((PlayerEventResponse) payload);
            }
        });
        session.send("/app/welcome", PlayerEventRequestTest.TestPlayerEventRequest());
        System.out.println(blockingQueue.toArray().length);
        assertEquals("test123", blockingQueue.poll(1, SECONDS));
    }

    private String getWsPath() {
        return String.format("ws://localhost:%d/ws-endpoint", port);
    }
}
