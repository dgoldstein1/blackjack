package com.davidgoldstein.blackjack.server;

import com.davidgoldstein.blackjack.api.PlayerEventRequest;
import com.davidgoldstein.blackjack.api.PlayerEventResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class PlayerMoveController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public PlayerEventResponse playerMove(PlayerEventRequest playerMove) throws Exception {
        return new PlayerEventResponse("Hello, " + HtmlUtils.htmlEscape(playerMove.initiator.getName()) + "!");
    }

}