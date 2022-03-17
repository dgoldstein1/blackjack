package davidgoldstein.blackjack.server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest
public class PlayerMoveController {
    @Autowired
    private WebSocketStompClient stompClient;

    @BeforeAll
    public void Setup() {
        StompSessionHandler sessionHandler = new CustmStompSessionHandler();

        StompSession stompSession = stompClient.connect(loggerServerQueueUrl,
                sessionHandler).get();



    }

    @Test
    void AbleToMakePlayerMove() {

    }
}
