package davidgoldstein.blackjack.server;

import davidgoldstein.blackjack.api.PlayerEventRequest;
import davidgoldstein.blackjack.api.PlayerEventResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class PlayerMoveController {
    @MessageMapping("/welcome")
    @SendTo("/topic/greetings")
    public PlayerEventResponse playerMove(@Payload PlayerEventRequest req) {
        return new PlayerEventResponse("SUCCESS" + req.initiator.getName());
    }
}