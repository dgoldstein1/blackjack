package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ActionController {

    @Autowired
    GameRepository gameRepository;

    /**
     * listens on actions to games, sends updates to /topics/actions/gameID
     * @param gameId
     * @param req
     * @return
     */
    @MessageMapping("/action/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public GameState makeMove(@DestinationVariable String gameId, ActionRequest req) throws GameNotFoundException {
        // TODO: validate request
        // TODO: convert to move
        System.out.println(req.getUserId());
        return new GameState();
        // TODO: set in repository
//        return gameRepository.setGameState(gameId, newGameState);
    }
}
