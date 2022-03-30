package davidgoldstein.blackjack.server;

import davidgoldstein.blackjack.api.PlayerEventRequest;
import davidgoldstein.blackjack.api.PlayerEventResponse;
import davidgoldstein.blackjack.api.Greeting;
import davidgoldstein.blackjack.api.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class PlayerMoveController {
    @MessageMapping("/welcome")
    @SendTo("/topic/greetings")
    public Greeting playerMove(HelloMessage message) throws Exception {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}