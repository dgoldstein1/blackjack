package davidgoldstein.blackjack;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTests {
    @Value("${local.server.port}")
    private int port;
    private String URL;

    private static final String SEND_CREATE_TABLE_ENDPOINT = "/app/create/";
    private static final String SEND_ACTION_REQUEST_ENDPOINT = "/app/action/";
    private static final String SUBSCRIBE_CREATE_TABLE_ENDPOINT = "/topic/table/";
    private static final String SUBSCRIBE_ACTION_REQUEST_ENDPOINT = "/topic/action/";
    private static final String SUBSCRIBE_ERROR_ENDPOINT = "/topic/error";

    private CompletableFuture<GameState> completableFuture;
    private CompletableFuture<String> completableStringFuture;

    @BeforeEach
    public void setup() {
        completableFuture = new CompletableFuture<>();
        completableStringFuture = new CompletableFuture<>();

        URL = "ws://localhost:" + port + "/game";
    }

    /**
     * creats a new game with a random UUID
     * @return
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    private StompSession newStompSession() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
    }


    @Test
    public void testCreateGame() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        StompSession ss = newStompSession();
        String uuid = UUID.randomUUID().toString();
        ss.subscribe(SUBSCRIBE_CREATE_TABLE_ENDPOINT + uuid, new CreateGameStompFrameHandler());
        ss.send(SEND_CREATE_TABLE_ENDPOINT + uuid, null);
        GameState gs = completableFuture.get(10, SECONDS);
        assertNotNull(gs);
    }

    @Test
    public void testCanReturnError() throws InterruptedException, ExecutionException, TimeoutException {
        StompSession ss = newStompSession();
        String uuid = UUID.randomUUID().toString();
        ss.subscribe(SUBSCRIBE_ERROR_ENDPOINT + uuid, new StringFrameHandler());
        ss.send(SEND_CREATE_TABLE_ENDPOINT + uuid, null);
        ss.send(SEND_CREATE_TABLE_ENDPOINT + uuid, null);
        String exception = completableStringFuture.get(5, SECONDS);
        assertNotNull(exception);
    }

    @Test
    public void testAction() throws InterruptedException, ExecutionException, TimeoutException {
        StompSession ss = newStompSession();
        String uuid = UUID.randomUUID().toString();
        ss.subscribe(SUBSCRIBE_ACTION_REQUEST_ENDPOINT + uuid, new CreateGameStompFrameHandler());
        ss.send(SEND_ACTION_REQUEST_ENDPOINT + uuid, new ActionRequest());
        GameState gameStateAfterACTION_REQUEST = completableFuture.get(5, SECONDS);
        assertNotNull(gameStateAfterACTION_REQUEST);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class StringFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            System.out.println(stompHeaders.toString());
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            System.out.println(o);
            completableStringFuture.complete((String) o);
        }
    }

    private class CreateGameStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            System.out.println(stompHeaders.toString());
            return GameState.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            System.out.println((GameState) o);
            completableFuture.complete((GameState) o);
        }
    }
}
