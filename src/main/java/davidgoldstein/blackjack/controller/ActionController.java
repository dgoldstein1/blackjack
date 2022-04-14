package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.model.ActionRequest;
import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ActionController {

    @Autowired
    GameService gameService;

    /**
     * handle exception is a separate topic to listen on errors
     * note that errors are only sent to the session the error originated from
     * @param exception
     * @return
     */
    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    /**
     * listens on actions to games, sends updates to /topics/actions/gameID
     * @param gameId
     * @param req
     * @return
     */
    @MessageMapping("/action/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public GameState makeMove(@DestinationVariable String gameId, ActionRequest req) throws Exception {
        GameState gs = null;
        try {
            gs = gameService.processAction(gameId, req);
        } catch (Exception e) {
            System.out.println("Could not process action " + req + ": " + e.getMessage());
            throw e;
        }
        return gs;
    }
}
