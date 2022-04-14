package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.model.ActionRequest;
import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// based on https://github.com/MBlokhuijzen/Spring-Websockets-IntegrationTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class ActionRequestControllerTests {
    @Value("${local.server.port}")
    private int port;
    private String URL;

    static final String GAME_ENDPOINT = "/rest/game";
    private static final String SEND_ACTION_REQUEST_ENDPOINT = "/app/action/";
    private static final String GAME_SUBSRIBE_ENDPOINT = "/topic/game/";
    private static final String SUBSCRIBE_ERROR_ENDPOINT = "/topic/error";
    private static final int TIMEOUT_S = 5;

    private CompletableFuture<GameState> completableFuture;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/game";
    }

    /**
     * creats a new game with a random UUID
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    private StompSession newStompSession() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(TIMEOUT_S, SECONDS);
    }


    @Test
    public void testStartGame() throws Exception {
        String gameId = UUID.randomUUID().toString();
        this.mockMvc
                .perform(post(GAME_ENDPOINT).param("gameId", gameId))
                .andExpect(status().isOk());

        StompSession ss = newStompSession();
        ss.subscribe(GAME_SUBSRIBE_ENDPOINT + gameId, new CreateGameStompFrameHandler());
        ss.send(SEND_ACTION_REQUEST_ENDPOINT + gameId, new ActionRequest(Action.START_GAME.toString(),UUID.randomUUID()));
        GameState gs = completableFuture.get(TIMEOUT_S, SECONDS);
        assertNotNull(gs);
        assertEquals(gameId, gs.getId());
        assertEquals(GameStatus.STARTED.toString(), gs.getStatus());
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class CreateGameStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            System.out.println(stompHeaders.toString());
            return GameState.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((GameState) o);
        }
    }
}
