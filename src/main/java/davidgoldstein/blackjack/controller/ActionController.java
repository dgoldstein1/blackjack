package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
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
    GameRepository gameRepository;

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
    public GameState makeMove(@DestinationVariable String gameId, ActionRequest req) throws GameNotFoundException {
        // validate request

//        gameRepository.retrieveById(gameId);
        // TODO: convert to move

        return new GameState();
//        return gameRepository.setGameState(gameId, curr);
    }
}
