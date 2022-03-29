package davidgoldstein.blackjack.server;

import davidgoldstein.blackjack.api.PlayerEventRequest;
import davidgoldstein.blackjack.api.PlayerEventResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class PlayerMoveController {
    @MessageMapping("/welcome")
    @SendTo("/topic/greetings")
    public String playerMove(String msg) throws Exception {
        return msg;
//        return new PlayerEventResponse("Hello, " + HtmlUtils.htmlEscape(playerMove.initiator.getName()) + "!");
    }
}